TKD JUDGE - Android WebView wrapper project

WHAT THIS PROJECT DOES
- Installs as an Android app named TKD JUDGE.
- Opens with a room-code entry screen.
- Connects to your existing Netlify judge page using:
  ?role=juge&room=YOUR_ROOM_CODE
- Keeps the existing website and Firebase-based scoring logic online.
- Requires an internet connection.

IMPORTANT
This is an Android Studio project, not a prebuilt APK. This workspace does not have the Android SDK/Gradle installed, so I could not compile and test an APK here. The APK can be built with Android Studio or the included GitHub Actions workflow.

BUILD WITH ANDROID STUDIO
1. Install Android Studio on a computer.
2. Open this project folder in Android Studio.
3. Allow Gradle sync and install Android SDK Platform 35 if prompted.
4. Choose Build > Build Bundle(s) / APK(s) > Build APK(s).
5. The debug APK will be at:
   app/build/outputs/apk/debug/app-debug.apk

BUILD USING THE INCLUDED GITHUB WORKFLOW
1. Create a new private repository on GitHub.
2. Upload all files in this project to the repository.
3. Open the Actions tab and run "Build TKD JUDGE APK" (or push to main).
4. When the workflow finishes, download the artifact named TKD-JUDGE-debug-apk.
5. The artifact contains app-debug.apk.

NOTES
- The app needs internet access because the scoreboard is hosted online.
- The website must remain available for the app to work.
- The app is a WebView wrapper; it does not make the scoring website work offline.
- The web page itself must be tested on the phone before using it in a real match.
- The app uses a generic TKD JUDGE icon and is not an official World Taekwondo app.
