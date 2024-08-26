##---------------Begin: proguard configuration for SQLCipher  ----------
-keep,includedescriptorclasses class net.sqlcipher.** { *; }
-keep,includedescriptorclasses interface net.sqlcipher.** { *; }


##---------------Begin: proguard configuration for Gson  ----------
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.examples.android.model.** { <fields>; }
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}


##---------------Begin: proguard configuration for Retrofit  ----------
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
-dontwarn kotlinx.**


##---------------Begin: proguard configuration for Glide  ----------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule { <init>(...); }
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** { **[] $VALUES; public *; }
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder { *** rewind(); }
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-dontwarn org.codehaus.mojo.animal_sniffer.*
-dontwarn okhttp3.internal.platform.ConscryptPlatform
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-keepnames class androidx.navigation.fragment.NavHostFragment
-dontwarn com.sun.javadoc.Doclet
-dontwarn java.lang.invoke.StringConcatFactory
-keep class java.lang.invoke.** {*;}


##---------------Begin: proguard configuration for App Classes  ----------
# Keep MyRecipeRepository and its members
-keep class com.dicoding.myrecipeapp.core.data.MyRecipeRepository { *; }
-keep class com.dicoding.myrecipeapp.core.data.Resource$Error { *; }
-keep class com.dicoding.myrecipeapp.core.data.Resource$Loading { *; }
-keep class com.dicoding.myrecipeapp.core.data.Resource$Success { *; }
-keep class com.dicoding.myrecipeapp.core.data.Resource { *; }

# Keep DataSource classes and their members
-keep class com.dicoding.myrecipeapp.core.data.source.local.LocalDataSource { *; }
-keep class com.dicoding.myrecipeapp.core.data.source.remote.RemoteDataSource { *; }
-keep class com.dicoding.myrecipeapp.core.data.source.remote.network.ApiService { *; }
-keep class com.dicoding.myrecipeapp.core.data.source.local.room.RecipeDao { *; }

# Keep DI modules and their providers
-keep class com.dicoding.myrecipeapp.core.di.** { *; }
-keep class com.dicoding.myrecipeapp.core.di.DatabaseModule { *; }
-keep class com.dicoding.myrecipeapp.core.di.DatabaseModule_ProvideDatabaseFactory { *; }
-keep class com.dicoding.myrecipeapp.core.di.DatabaseModule_ProvideRecipeDaoFactory { *; }
-keep class com.dicoding.myrecipeapp.core.di.NetworkModule { *; }
-keep class com.dicoding.myrecipeapp.core.di.NetworkModule_ProvideApiServiceFactory { *; }
-keep class com.dicoding.myrecipeapp.core.di.NetworkModule_ProvideOkHttpClientFactory { *; }

# Keep domain classes and their members
-keep class com.dicoding.myrecipeapp.core.domain.model.Recipe { *; }
-keep class com.dicoding.myrecipeapp.core.domain.repository.IRecipeRepository { *; }
-keep class com.dicoding.myrecipeapp.core.domain.usecase.RecipeInteractor { *; }
-keep class com.dicoding.myrecipeapp.core.domain.usecase.RecipeUseCase { *; }

# Keep UI classes and their members
-keep class com.dicoding.myrecipeapp.core.ui.MyRecipeAdapter { *; }

# Keep utility classes
-keep class com.dicoding.myrecipeapp.core.utils.** { *; }
-keep class com.dicoding.myrecipeapp.core.utils.AppExecutors { *; }
-keep class com.dicoding.myrecipeapp.core.utils.DarkMode { *; }
-keep class com.dicoding.myrecipeapp.core.utils.UtilsKt { *; }
-keep class com.dicoding.myrecipeapp.core.utils.DataMapper { *; }

# Keep Hilt components
-keep class * implements dagger.hilt.internal.GeneratedComponent { *; }
-keep class * implements dagger.hilt.internal.GeneratedComponentManager { *; }
-keep class * implements dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }
-keep @dagger.hilt.android.HiltAndroidApp class *
-keep @dagger.hilt.android.AndroidEntryPoint class *
-keep @dagger.hilt.InstallIn class *
-keep @dagger.Module class *
-keep @dagger.hilt.components.SingletonComponent class *
-keep @dagger.hilt.android.components.ActivityComponent class *
-keep @dagger.hilt.android.components.FragmentComponent class *
-keep @dagger.Module class * { *; }
-keep class * {
    @dagger.Provides <methods>;
}
-keepattributes *Annotation*

# keep jsonup
-keep class org.jsoup.** { *; }
-dontwarn org.jsoup.**
