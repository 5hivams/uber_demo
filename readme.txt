Uber Coding Challenge
============

This is my result of the coding challenge I am participating in. I made this pretty quickly, but then made a pretty big overhaul of the whole thing to make things look prettier.

- [The task](#the-task)
- [Features](#features)
- [Configuration](#configuration)
- [Architecture](#architecture)

The task
--------

Write a mobile app that uses the Flickr image search API and shows the results in a 3-column scrollable view.

**Requirements:**

1. A working app. Shortcuts are fine given the time constraints but be prepared to
justify them and explain better solutions you would have implemented with more
time.
2. Clean code and architecture. We would like you to write production-ready code
that you would be proud to submit as an open source project, including a
reassuring amount of unit test coverage.
We expect this to take about 5 hours so no need to implement features that would obviously
require more time than that. A concise and readable codebase that accomplishes all of the
above requirements is the goal. Thus, do not try to do any more than is required to solve the
problem cleanly. Finally, if you need more time to be proud of your delivered code, it's okay,
just let us know how much time you spent on the project.

Good luck!


----------


**Flickr API**
You can make this call to the Flickr API to return a JSON object with a list of photos.

[https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1&safe_search=1&text=kittens](https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1&safe_search=1&text=kittens)

The text parameter should be replaced with the query that the user enters into the app.

The JSON response you'll receive will have items described like this example.

```
{
"id": "23451156376",
"owner": "28017113@N08",
"secret": "8983a8ebc7",
"server": "578",
"farm": 1,
"title": "Merry Christmas!",
"ispublic": 1,
"isfriend": 0,
"isfamily": 0
},
```

You can use these parameters to get the full URL of the photo:

```
http://farm{farm}.static.flickr.com/{server}/{id}_{secret}.jpg
```

So, using our example from before, the URL would be

```
http://farm1.static.flickr.com/578/23451156376_8983a8ebc7.jpg
```

If interested, more documentation about the search endpoint can be found at https://www.flickr.com/services/api/explore/flickr.photos.search. If you have any problems with the Uber-specified API key, then you can generate your own at https://www.flickr.com/services/api/misc.api_keys.html.

Features:
---------

- Searching the Flickr Database for images
- Image caching, To save network and time
- Infinite scroll
- No use of any third party library

Configuration
-------------

All values used by the app are stored in *Config.swift*.
You can change the following values:

- Flickr API Key
- Flickr API Url
- Backgroundcolor of views
- Number of items per page
- Number of columns

```swift
static let APIKey = "1234567890abcdefghijklmnopqrstuvwxyz"
static let MainColor = UIColor.blackColor()
static let ItemsPerPage = 20
```

Architecture
--------
To make sure all the images are laid out nicely, I have subclassed `ImageLoader`, where I am calculating the position for every image in the `GridView`. The standard `GridView` had the big disadvantage, that all items were lined up evenly, which resulted in having vertical spaces between images, since these images don't share the same aspect ratio and the `GridViewLayout` does not take care of that.

Network requests are made using standard HttpURLConnection.

Image caching. To save network and time, we would like you to implement a
caching mechanism for the photos displayed in the app. Again, no third-party
libraries can be used.