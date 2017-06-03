# Vials
Ths mod adds Tinker's Construct sized fluid containers, called Vials. 

*This mod requires Java 8!*

## License / Use in Modpacks
This mod is [licensed under the **MIT license**](LICENSE). All **assets are public domain**, unless otherwise stated; all are free to be distributed as long as the license / source credits are kept. This means you can use this mod in any mod pack **as you please**. I'd be happy to hear about you using it, though, just out of curiosity.



### Gradle
To add a dependency to Vials for use in your mod, add the following to your `build.gradle`:

```groovy
repositories {
  maven {
    url = "http://maven.cil.li/"
  }
}
dependencies {
  compile "li.cil.vials:Vials:${config.vials.version}"
}
```

Where `${config.vials.version}` is the version you'd like to build against.
