[![](https://jitpack.io/v/adawoud/BottomSheetTimeRangePicker.svg)](https://jitpack.io/#adawoud/BottomSheetTimeRangePicker)[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-BottomSheetTimeRangePicker-green.svg?style=flat )]( https://android-arsenal.com/details/1/7367 )

# BottomSheetPickers

BottomSheetPickers is a tiny Android library for adding Date and Time Pickers as Modal BottomSheetDialogs with From and To ranges. 

The library is inspired by Titto Jose's [TimeRangePicker](https://github.com/tittojose/TimeRangePicker).

## Screenshots

<img src="https://raw.githubusercontent.com/adawoud/BottomSheetTimeRangePicker/master/art/bottomsheetpicker.png" width="300px" />

## Installation

Add Jitpack to your project build.gralde file
      
      allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
}

Then add this dependency to your app build.gradle file.

      dependencies {
	        implementation 'com.github.adawoud:BottomSheetTimeRangePicker:latest-release'
	}

## Usage

Make sure your Activity/Fragment implements `OnTimeRangeSelectedListener`, and then you 
can just do this:
      
	BottomSheetTimeRangePicker
		.newInstance(this, DateFormat.is24HourFormat(this))
		.show(supportFragmentManager, tagBottomSheetTimeRangePicker)
		    
You can see this in action in the sample app [here](https://github.com/adawoud/BottomSheetTimeRangePicker/blob/44ec220fd548256df8182bb5ab0d76f25af03d0c/sample/src/main/java/me/adawoud/bottomsheettimepicker/MainActivity.kt#L16)

## Customization
      
You can customize things like the text displayed in the From and To tabs, the text that appears on the positive action button,
and set intial times for both timepickers

	BottomSheetTimeRangePicker
		.tabLabels(startTabLabel = "Hello", endTabLabel = "World")
		.doneButtonLabel("Ok")
		.startTimeInitialHour(2)
        	.startTimeInitialMinute(11)
        	.endTimeInitialHour(10)
        	.endTimeInitialMinute(22)
        	.newInstance(this, DateFormat.is24HourFormat(this))
       	.show(supportFragmentManager, tagBottomSheetTimeRangePicker)

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
