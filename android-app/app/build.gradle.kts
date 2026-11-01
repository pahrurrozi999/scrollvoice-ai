plugins {
        id("com.android.application")
            id("org.jetbrains.kotlin.android")
}

val releaseStoreFilePath = System.getenv("RELEASE_STORE_FILE")
val releaseStorePasswordValue = System.getenv("RELEASE_STORE_PASSWORD")
val releaseKeyAliasValue = System.getenv("RELEASE_KEY_ALIAS")
val releaseKeyPasswordValue = System.getenv("RELEASE_KEY_PASSWORD")

android {
        namespace = "com.scrollvoice.ai"
            compileSdk = 35

                compileOptions {
                            sourceCompatibility = JavaVersion.VERSION_17
                                    targetCompatibility = JavaVersion.VERSION_17
                }

                    kotlinOptions {
                                jvmTarget = "17"
                    }

                        defaultConfig {
                                    applicationId = "com.scrollvoice.ai"
                                            minSdk = 26
                                                    targetSdk = 35
                                                            versionCode = 1
                                                                    versionName = "1.0"
                        }

                            signingConfigs {
                                        create("release") {
                                                        storeFile = releaseStoreFilePath?.let { file(it) }
                                                                    storePassword = releaseStorePasswordValue
                                                                                keyAlias = releaseKeyAliasValue
                                                                                            keyPassword = releaseKeyPasswordValue
                                        }
                            }

                                buildTypes {
                                            getByName("release") {
                                                            signingConfig = signingConfigs.getByName("release")
                                                                        isMinifyEnabled = false
                                            }
                                }
}
                                        }
                }
}
