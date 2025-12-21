plugins {
    kotlin("jvm") version "2.2.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.dealspot"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // AWS Lambda Java Core
    implementation("com.amazonaws:aws-lambda-java-core:1.2.3")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.3")
    
    // AWS SDK v2 for DynamoDB (default)
    implementation(platform("software.amazon.awssdk:bom:2.20.0"))
    implementation("software.amazon.awssdk:dynamodb")
    
    // AWS SDK v1 for DynamoDB (alternative - uncomment if using CreateDealHandlerV1)
    // implementation("com.amazonaws:aws-java-sdk-dynamodb:1.12.500")
    
    // JSON parsing
    implementation("com.google.code.gson:gson:2.10.1")
    
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.2.20")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
    
    shadowJar {
        archiveBaseName.set("create-deal-handler")
        archiveClassifier.set("")
        archiveVersion.set("")
    }
}

