import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider

operator fun ExtraPropertiesExtension.contains(key: String) = has(key)

operator fun <T> Property<T>.invoke(value: T): Unit = this.set(value)
operator fun <T> Property<T>.invoke(value: Provider<T>): Unit = this.set(value)

operator fun <T> ListProperty<T>.invoke(value: T, vararg values: T): Unit = this.addAll(value, *values)
operator fun <T> ListProperty<T>.invoke(values: Iterable<T>): Unit = this.addAll(values)
operator fun <T> ListProperty<T>.invoke(values: Provider<Iterable<T>>): Unit = this.addAll(values)

operator fun <T> Provider<T>.invoke(): T = this.get()
