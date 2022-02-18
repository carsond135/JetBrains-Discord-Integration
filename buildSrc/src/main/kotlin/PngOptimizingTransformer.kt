import com.github.jengelman.gradle.plugins.shadow.transformers.CacheableTransformer
import com.github.jengelman.gradle.plugins.shadow.transformers.Transformer
import com.github.jengelman.gradle.plugins.shadow.transformers.TransformerContext
import com.googlecode.pngtastic.core.PngImage
import com.googlecode.pngtastic.core.PngOptimizer
import net.openhft.hashing.LongHashFunction
import org.apache.commons.io.IOUtils
import org.gradle.api.Project
import org.gradle.api.file.FileTreeElement
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import shadow.org.apache.tools.zip.ZipEntry
import shadow.org.apache.tools.zip.ZipOutputStream
import java.awt.Image
import java.awt.image.BufferedImage
import java.awt.image.RenderedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.nio.file.StandardOpenOption
import javax.imageio.IIOImage
import javax.imageio.ImageIO

@Suppress("FunctionName")
fun Project.PngOptimizingTransformer(size: Int, vararg includePaths: Regex): Transformer =
    PngOptimizingTransformer(size, buildDir.toPath().resolve("cache/icons"), *includePaths)

@CacheableTransformer
class PngOptimizingTransformer(
    @get:Input
    val size: Int,

    @get:Internal
    val cacheDir: Path,

    @get:Input
    vararg val includePaths: Regex
) : Transformer {

    private val files = mutableMapOf<String, Path>()

    private val cacheFileTemp: Path = cacheDir.resolve("new")

    override fun getName() = "PngOptimizingTransformer"

    override fun canTransformResource(element: FileTreeElement): Boolean {
        val path = element.relativePath.pathString
        return includePaths.any { it.matches(path) }
    }

    override fun hasTransformedResource(): Boolean = files.isNotEmpty()

    private val byteArrayOutputStream = ByteArrayOutputStream()

    override fun transform(context: TransformerContext) {
        val data = IOUtils.toByteArray(context.`is`)

        @Suppress("EXPERIMENTAL_API_USAGE")
        val hash = java.lang.Long.toUnsignedString(LongHashFunction.xx().hashBytes(data))

        val cacheFile = cacheDir.resolve("$hash-$size")

        if (!Files.exists(cacheFile)) {
            val image: BufferedImage = ImageIO.read(ByteArrayInputStream(data)) ?: return

            val scaledImage: RenderedImage = image.getScaledInstance(size, size, Image.SCALE_SMOOTH).getRenderedImage()
            val imageOutputStream = ImageIO.createImageOutputStream(byteArrayOutputStream)

            val writer = ImageIO.getImageWritersByFormatName("png").next()
            val param = writer.defaultWriteParam

            writer.output = imageOutputStream
            writer.write(null, IIOImage(scaledImage, null, null), param)

            val pngImage = PngImage(byteArrayOutputStream.toByteArray())
            val optimizer = PngOptimizer()
            val optimizedPngImage: PngImage = optimizer.optimize(pngImage)

            byteArrayOutputStream.reset()

            Files.createDirectories(cacheDir)

            Files.newOutputStream(cacheFileTemp, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING).use {
            	optimizedPngImage.writeDataOutputStream(it)
            }

            Files.move(cacheFileTemp, cacheFile, StandardCopyOption.REPLACE_EXISTING)
        }

        files[context.path] = cacheFile
    }

    override fun modifyOutputStream(os: ZipOutputStream, preserveFileTimestamps: Boolean) {
    	for ((path, cachePath) in files) {
    		val entry = ZipEntry(path)
    		entry.time = TransformerContext.getEntryTimestamp(preserveFileTimestamps, entry.time)
    		os.putNextEntry(entry)
    		IOUtils.copy(Files.newInputStream(cachePath), os)
    	}
    }

    private fun Image.getRenderedImage(): RenderedImage {
    	if (this is RenderedImage) return this

    	val image = BufferedImage(getWidth(null), getHeight(null), BufferedImage.TYPE_INT_ARGB)

    	val graphics = image.createGraphics()
    	graphics.drawImage(this, 0, 0, null)
    	graphics.dispose()

    	return image
    }
}
