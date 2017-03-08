# Android Video Compressor  And recording

Video Compressor based in the [**Telegram for Android**](https://github.com/DrKLO/Telegram) [![Telegram for Android](https://raw.githubusercontent.com/lalongooo/VideoCompressor/master/images/ic_launcher.png)](https://github.com/DrKLO/Telegram) app source code.

#### Important:

 
  Add video recording and compression progress callback
 * This project is based on an older version of the Telegram for Android app. A version from one year ago.
* It is supposed to work on Android API 16+, however has not been tested properly on Marshmallow and above.
* No major issues have been reported on Lollipop.
* In order to have this project working correctly, it needs to be  updated as the official Telegram for Android app does. No ETA at this time.



Confusion statement
dontwarn com.googlecode.mp4parser.**

-dontwarn com.coremedia.iso.boxes.**

-keep class com.coremedia.iso.boxes.**{ *; }

-dontwarn com.mp4parser.**

-keep class isoparser-1.0.6.** { *; }

-dontwarn isoparser-1.0.6.**

-keep public class * implements com.coremedia.iso.boxes.Box
