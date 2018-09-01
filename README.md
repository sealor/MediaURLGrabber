Android App MediaURLGrabber [![Build Status](https://travis-ci.org/sealor/MediaURLGrabber.svg?branch=master)](https://travis-ci.org/sealor/MediaURLGrabber)
===============

With the MediaURLGrabber you can send media URLs (e.g. from YouTube) to your [Kodi Media Center](https://kodi.tv/) via [Kore App ](https://github.com/xbmc/Kore) for playing.

How It Works
------------
1. build and install the MediaURLGrabber
2. visit a YouTube video via Chrome or YouTube App
3. share the video with MediaURLGrabber
4. press "SEND URL"
5. watch the video with Kodi

Current Features
----------------
- sends URLs to Kore App
- support for [ARD Mediathek](https://www.ardmediathek.de) and [YouTube](https://www.youtube.com)

Planned Features
----------------
- send URLs to UPnP/DLNA devices
- support for more audio and video websites
- provide the grabbing library for other Open Source projects

Known Issues
------------
- the App is still in a very early stage (pre-alpha)
- websites which are using client-side routing (PushState) need to be reloaded before sharing the URL

Development
-----------
- Your feedback (e.g. bug reports) is very welcome. You can also contribute via pull-requests.
- Last master build: [![Build Status](https://travis-ci.org/sealor/MediaURLGrabber.svg?branch=master)](https://travis-ci.org/sealor/MediaURLGrabber)
