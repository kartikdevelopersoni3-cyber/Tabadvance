# ProGuard rules for Roohi Android app

-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Keep all classes
-keep class com.roohi.** { *; }

# Kotlin
-keepclassmembers class * {
    *** toString();
}

# Hilt
-keep @dagger.hilt.** class *
