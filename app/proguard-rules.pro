-keepattributes Signature
-keepattributes *Annotation*

# Transport-agnostic request/response models.
-keep class com.vibeout.talaa.core.network.dto.** { *; }

# Cloud Firestore deserializes documents into these models via reflection, so
# their fields and no-argument constructors must be preserved under R8.
-keep class com.vibeout.talaa.core.model.** { *; }
-keepclassmembers class com.vibeout.talaa.core.model.** {
    <init>();
    <fields>;
}

# Firestore relies on generic type information at runtime.
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault

-dontwarn javax.annotation.**
