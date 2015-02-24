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

- inspects bundle capabilities and requirements  

parameters:  
      String   ('capability' | 'requirement')  
      String   (<namespace> | 'service')  
      Bundle[]   target bundles  

Samples:

```
g! inspect cap *			# show all capability of all bundles
g! inspect cap * 1			# show all capability of 1 bundle
g! inspect cap *package* 1	# show all capability (*package*) of 1 bundle
g! inspect cap *package* 	# show all capability (*package*) of all bundles
g! inspect cap service 1	# show all capability (service) of 1 bundle
g! inspect cap service	 	# show all capability (service) of all bundles

g! inspect req *			# show all requirements of all bundles
g! inspect req * 1			# show all requirements of 1 bundle
g! inspect req *package* 1	# show all requirements (*package*) of 1 bundle
g! inspect req *package* 	# show all requirements (*package*) of all bundles
g! inspect cap service 1	# show all requirements (service) of 1 bundle
g! inspect cap service	 	# show all requirements (service) of all bundles
```

## general gogo:commands

***gogo:set***

- show session context
- set a variable into session context `variable = value`
- get value `$variable` or `${variable}` 

Sample:

```
g! set
null            0               null
String          SCOPE           gogo:*						# scope of last command					
null            _               null						# return value of last command ( null == void )
Closure         e               $exception printStackTrace	# short cut '$e' for '$exception printStackTrace' 
IllegalArgument exception       java.lang.IllegalArgumentException: Cannot co...	# last cached exception
HeapCharBuffer  prompt          g! 							# ?
```

***gogo:echo ['objects']***

- print 'object(s).toString' to System.out

***gogo:grep***

- grep System.out lines 

Sample:

```
Successful greps:

g! echo test | grep test
g! echo test | grep .est
g! echo test | grep '[tT]est'
g! echo Test | grep '[tT]est'
g! echo test | grep t*t
g! echo test | grep t*

Errors:

g! echo test | grep *est
Fix: echo test | grep '$*est'

g! echo test | grep [tT]est
Fix: echo test | grep '[tT]est' 
``` 

***gogo:cat 'resource'***

- read content of 'resource' 
- to System.out

```
g! cat .project				# show content of .project file if exist.
``

Limitation:  
- no-'resource' parameter trigger a loop

***gogo:tac***

- collect content from System.in 
- to ' '-seperated string 
- as return 

Usage: tac [-al] [FILE]  
  -a --append              append to FILE  
  -l --list                return List<String>  
  -? --help                show help  

Sample:  

```
g! cat .project | tac		# collect content of .project into onle line
g! xml =(cat .project | tac)# collect content of .project into $xml variable
```


Limitation:  
- successful tested only behind '|'

## script gogo:commands

***gogo:sh 'path'***

- run a script file
- last statement == return value

```
g! sh ./script.sh			# '/absolute/paths' impossible
```

***gogo:gosh 'path'***

- run a script file
- last statement == return value

```
g! gosh ./script.sh			# '/absolute/paths' impossible
```

***gogo:source 'path'***

- run a script file
- last statement == return value


```
g! source ./script.sh			# '/absolute/paths' impossible
```

## internal gogo:commands

***gogo:type***

- show type of command

Usage: type [OPTIONS] [name[:]]  
  -a --all                 show all matches  
  -? --help                show help  
  -q --quiet               don't print anything, just return status  
  -s --scope=NAME          list all commands in named scope  
  -t --types               show full java type names  

Sample:

```
g! type -a -t echo 
void org.apache.felix.gogo.shell.Posix.echo(Object[])
```

***gogo:getopt 'help' ['args']***

- parse command-'help' like that:

Usage: type [OPTIONS] [name[:]]  
  -a --all                 show all matches  
  -? --help                show help  
  -q --quiet               don't print anything, just return status  
  -s --scope=NAME          list all commands in named scope  
  -t --types               show full java type names  

- to parse 'args' 
- and return a option-instance.


> Helper command to simplyfy command-args parsing.  

***gogo:format 'obj'*** 

- try to format given 'obj'
- with registered 'converter'
- or call tostring

and print to System.out

***gogo:telnetd***

- untested

## language gogo:commands

***gogo:each***

- iterate about a collection 
- and yield the entry as $it

Sample: 

```
g! each [1 2 3] { bundle $it }	
    1|Active     |    1|org.apache.felix.gogo.shell (0.10.0)
    2|Active     |    1|org.apache.felix.gogo.command (0.14.0)
    3|Active     |    1|org.apache.felix.gogo.runtime (0.12.1)
```  

***gogo:not***

- untested

***gogo:until***

- untested

## repository obr:commands

> require a instance of RepositoryAdmin eg.'org.apache.felix.bundlerepository-2.0.2.jar'

***obr:repos***

repos - manage repositories

parameters:  
      String   ( add | list | refresh | remove )  
      String[]   space-delimited list of repository URLs 

- 'obr:repos add 'url' 			# register as repository 
- 'obr:repos list 	 			# register as repository
- 'obr:repos refresh 'url|id'	# reload index.xml (e.g after new-generation)
- 'obr:repos remove 'url|id'	# remove as repository

***obr:list***

- list 'bundleSymbolicNames' 
- and version of repo-resources

Sample:

```
g! obr:list
com.google.guava (14.0.1)
biz.aQute.junit (1.2.0.20140510-060840)
```

***obr:info 'bundleSymbolicName'***

- list capabilities and requirements 
- of 'bundleSymbolicName' from repo-resource

Sample:

```
g! obr:info com.google.guava
----------------
com.google.guava
----------------
symbolicname: com.google.guava
mime: application/vnd.osgi.bundle
type: osgi.bundle
uri: file:/Users/winter/work/bnd/gogo.shell/gogo.shell/obr/guava-14.0.1.jar
size: 2189117
version: 14.0.1
Requires:
   (package=javax.annotation)
   (package=javax.inject)
   (package=sun.misc)
Capabilities:
   {osgi.wiring.bundle=com.google.guava, bundle-version=14.0.1}
   {osgi.wiring.host=com.google.guava, bundle-version=14.0.1}
   {bundle-version=14.0.1, bundle-symbolic-name=com.google.guava, osgi.wiring.package=com.google.common.net, version=14.0.1}
   {bundle-version=14.0.1, bundle-symbolic-name=com.google.guava, osgi.wiring.package=com.google.common.collect, version=14.0.1}
   {bundle-version=14.0.1, bundle-symbolic-name=com.google.guava, osgi.wiring.package=com.google.common.primitives, version=14.0.1}
   {bundle-version=14.0.1, bundle-symbolic-name=com.google.guava, osgi.wiring.package=com.google.common.base, version=14.0.1}
   {bundle-version=14.0.1, bundle-symbolic-name=com.google.guava, osgi.wiring.package=com.google.common.cache, version=14.0.1}
   {bundle-version=14.0.1, bundle-symbolic-name=com.google.guava, osgi.wiring.package=com.google.common.eventbus, version=14.0.1}
   {bundle-version=14.0.1, bundle-symbolic-name=com.google.guava, osgi.wiring.package=com.google.common.util.concurrent, version=14.0.1}
   {bundle-version=14.0.1, bundle-symbolic-name=com.google.guava, osgi.wiring.package=com.google.common.hash, version=14.0.1}
   {bundle-version=14.0.1, bundle-symbolic-name=com.google.guava, osgi.wiring.package=com.google.common.io, version=14.0.1}
   {bundle-version=14.0.1, bundle-symbolic-name=com.google.guava, osgi.wiring.package=com.google.common.reflect, version=14.0.1}
   {bundle-version=14.0.1, bundle-symbolic-name=com.google.guava, osgi.wiring.package=com.google.common.math, version=14.0.1}
   {bundle-version=14.0.1, bundle-symbolic-name=com.google.guava, osgi.wiring.package=com.google.common.annotations, version=14.0.1}
``` 


***obr:deploy 'bundleSymbolicName'***

- try to install 'bundleSymbolicName'
- into runtime container

Sample:

```
g! obr:list
com.google.guava (14.0.1)
biz.aQute.junit (1.2.0.20140510-060840)
g! obr:deploy com.google.guava 
Target resource(s):
-------------------
   null (14.0.1)

Deploying...
done.
``` 

Limitation:

```
g! obr:list
com.google.guava (14.0.1)
biz.aQute.junit (1.2.0.20140510-060840)
g! obr:deploy biz.aQute.junit
Unsatisfied requirement(s):
---------------------------
   (&(osgi.ee=JavaSE)(version=1.5))

# EVEN THOUGH:
g! inspect cap osgi.ee 0
org.apache.felix.framework [0] provides:
----------------------------------------
osgi.ee; OSGi/Minimum [1.0.0, 1.1.0, 1.2.0] [UNUSED]
osgi.ee; JavaSE [1.0.0, 1.1.0, 1.2.0, 1.3.0, 1.4.0, 1.5.0, 1.6.0, 1.7.0] required by:
   gogo.shell [6]

```


***obr:javadoc***

- retrieve resource JavaDoc from repository

flags:  
      -x, --extract   extract documentation  
parameters:  
      File   local target directory  
      String[]   ( <bundle-name> | <symbolic-name> | <bundle-id> )[@<version>] ...  

> untested

***obr:source 'bundleSymbolicName'***

- try to 'load/show/.../?' sources of 'bundleSymbolicName'
- from registered repos

> untested
