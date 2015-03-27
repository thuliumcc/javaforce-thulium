JavaForce SDK
=============

Version 7.30.0

What is it?
===========
JavaForce is a central library extending the capabilities of Java.

The main library includes a VoIP stack and JNA bindings for FFMPEG, OpenGL, etc.

Includes many apps built around the library such as jPhoneLite, jfVideo Createor, jfAudio, jfMusic, etc.

JF is also the core library in the Java infused Linux Operating system : http://jfLinux.org


Projects
========
jPhoneLite - Java VoIP/SIP soft phone
  6 lines, g711, g729a, Xfer, Hold, Conference, contact list, recent list, RFC 2833, etc.

jfVideo Creator - video production

jfPaint - a multi-tabbed image editor

jPBXlite - Java VoIP/SIP PBX
  - extensions, trunks, voicemail, IVRs, conferences

jfBroadcast - VoIP/SIP Auto Dialer System

jfTerm - a great Telnet client application that includes support for:
  Telnet/ANSI (full color).
  SSH (X11) (using JCraft JSch)
  SSL
  Multi-tabbed.
  Copy/Paste.
  Logging.

jfEdit - a multi-tabbed text editor.

jfHex - a multi-tabbed hex editor.

and many more...

Folders
=======
 /          - main folder (run ant here to build /src)
 /src       - the javaforce source files
 /jars      - 3rd party files
 /classes   - javaforce compiled files
 /projects  - source for all sub-projects

Classpath
=========
No special CLASSPATH is required except maybe "." to run applications of course.

Building
========
All projects are built with Apache Ant (available at http://ant.apache.org).
Make sure to run ant in the main folder to build the /src folder and then in any of the apps in /projects.

3rd party
=========
All third party dependancies are in /jars

Javadoc
=======
You can run 'ant javadoc' to create /javadoc and load index.html to view them.

License
=======
JavaForce itself is licensed under the LGPL license which can be read in license.txt.
The other jars in /jars may each have their own licensing.
  filters.jar - jhlabs.com - Apache License 2.0
  jna*.jar - LGPL 2.1 and Apache License 2.0
  bouncycastle.jar - http://www.bouncycastle.org/licence.html

Enjoy!

Peter Quiring
pquiring@gmail.com

http://javaforce.sourceforge.net

Version 7.30.0

Released : September 15 2014

