[ ![Download](https://api.bintray.com/packages/adawoud/BottomSheetTimeRangePicker/com.adawoud.bottomsheettimerangepicker/images/download.svg) ](https://bintray.com/adawoud/BottomSheetTimeRangePicker/com.adawoud.bottomsheettimerangepicker/_latestVersion)


# BottomSheetPickers

BottomSheetPickers is a tiny Android library for adding Date and Time Pickers as Modal BottomSheetDialogs with From and To ranges. 

The library is inspired by Titto Jose's [TimeRangePicker](https://github.com/tittojose/TimeRangePicker).

## Installation

Add the following to your project build.gralde file
      
      allprojects {
            repositories {
                  maven {
                        url "https://dl.bintray.com/adawoud/BottomSheetTimeRangePicker/"
                  }
      }
}

Then add this dependency to your app build.gradle file.

      implementation 'com.adawoud:bottomsheettimerangepicker:latest-version'

## Usage

Make sure your Activity/Fragment implements `BottomSheetTimeRangePicker.OnTimeRangeSelectedListener`, and then you 
can just do this:
      
      BottomSheetTimeRangePicker
            .newInstance(this, DateFormat.is24HourFormat(this))
            .show(supportFragmentManager, tagBottomSheetTimeRangePicker)
You can see this in action in the sample app [here](https://github.com/adawoud/BottomSheetTimeRangePicker/blob/44ec220fd548256df8182bb5ab0d76f25af03d0c/sample/src/main/java/me/adawoud/bottomsheettimepicker/MainActivity.kt#L16)


## License

Copyright 2018 Ahmad Dawoud

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
         
      http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
