# gogo.shell commands

## general felix:commands

***felix:help	[command]***

> show help for all or one 'command'
> 'command' is optional

## bundle felix:commands

felix:install  'url'

> install a jar as bundle
> 'url' : file:/absolute/path/to.jar

Sample:

`g! install 'file:/absolute/path/to.jar'`

Limitation:

> 'file:/...' is valid 'file://...' not

felix:uninstall 'bundleId'

> uninstall a bundle from container

felix:refresh  'bundleId'

> 
felix:update
felix:resolve
felix:start
felix:stop
felix:bundlelevel
felix:frameworklevel
felix:log

---

felix:lb
felix:headers
felix:inspect

---

felix:cd
felix:ls
felix:which

---

gogo:set
gogo:echo
gogo:grep
gogo:cat
gogo:tac

---

gogo:sh
gogo:gosh
gogo:source

---

gogo:type
gogo:getopt
gogo:format

---

gogo:telnetd

---

gogo:each
gogo:not
gogo:until

---



obr:deploy
obr:info
obr:javadoc
obr:list
obr:repos
obr:source
