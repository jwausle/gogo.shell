# gogo.shell commands

**[general felix:commands](#general-felixcommands)**  
**[bundle lifecycle felix:commands](#bundle-lifecycle-felixcommands)**  
**[bundle inspection felix:commands](#bundle-inspection-felixcommands)**  
**[general gogo:commands](#general-gogocommands)**  
**[script gogo:commands](#script-gogocommands)**  
**[internal gogo:commands](#internal-gogocommands)**  
**[language gogo:commands](#language-gogocommands)**  
**[repository obr:commands](#repository-obrcommands)**  

## general felix:commands

***felix:help	[command]***

- show help for all or one 'command'  
- 'command' is optional

```
g! help echo 

echo
   scope: gogo
   parameters:
      Object[] 
```

***felix:cd ['path']***

- change directory to path
- default-root='user.dir'

Limitation:

```
g! cd /tmp		# absolute paths impossible
```

***felix:ls ['path']***

- list content dir
- list actual dir 					( if noparam )
- list content of actual-dir/path	( if path-exist )

***felix:which 'class'***

- determines from where a bundle loads a class

parameters:  
      Bundle   target bundle  
      String   target class name  

## bundle lifecycle felix:commands

***felix:install  'url'***

- install a jar as bundle
- 'url' : file:/absolute/path/to.jar

Sample:

`g! install 'file:/absolute/path/to.jar'`

Limitation:

```
g! install 'file:/ ...' 	# is valid 
g! install 'file://...' 	# is invalid
```

***felix:uninstall 'bundleId'***

- uninstall a bundle from container
- 'bundleId: 1...N 

***felix:refresh  'bundleId'***

- refresh wired packages and services
- use 'PackageAdmin' to refresh packages and services 
 
***felix:update 'bundleId'***

- stop & reload bundle.jar

***felix:resolve 'bundleId'***

- resolve bundle 
- use 'PackageAdmin' to resolve bundle

***felix:start 'bundleId'***

- start bundle 

***felix:stop 'bundleId'***

- stop bundle

***felix:bundlelevel ['startlevel' 'bundleId']***

- show bundlelevel of bundles 		( if noparam )
- set 'startlevel' for 'bundleId' 	( if 2-params exist )

If bundlelevel > frameworklevel will bundle stop.

***felix:frameworklevel ['startlevel']***

- show frameworklevel of container	( if noparam )
- set 'startlevel' of container		( if 1-param exist )

If bundlelevel > frameworklevel will bundles stop.

***felix:log 'message'***

- log a message to log-service.

## bundle inspection felix:commands

***felix:lb ['flags'] ['bsn']***

- list installed bundles matching a substring
 
flags:  
      -l, --location   show location  
      -s, --symbolicname   show symbolic name  
      -u, --updatelocation   show update location  
parameters:  
      bsn   subtring matched against name or symbolic name  

***felix:headers ['bundleId'] ***

- show all headers 		( if noparam )
- show bundle-header 	( if 1-param exist )


***felix:inspect***


## general gogo:commands

***gogo:set***

***gogo:echo***

***gogo:grep***

***gogo:cat***

***gogo:tac***

## script gogo:commands

***gogo:sh***

***gogo:gosh***

***gogo:source***

## internal gogo:commands

***gogo:type***

***gogo:getopt***

***gogo:format***

***gogo:telnetd***


## language gogo:commands

***gogo:each***

***gogo:not***

***gogo:until***

## repository obr:commands

***obr:deploy***

***obr:info***

***obr:javadoc***

***obr:list***

***obr:repos***

***obr:source***

