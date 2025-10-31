import SwiftUI
import GoogleMaps

@main
struct iOSApp: App {
    init() {
        // Provide your Google Maps iOS API key here
       GMSServices.provideAPIKey("AIzaSyAtG3IhC7f3F-p3nImQbED-xuOALCR8LKU")
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
