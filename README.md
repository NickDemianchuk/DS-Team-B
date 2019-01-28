# Homework 1 [![Build Status](https://travis-ci.com/NickDemianchuk/DS-Team-B.svg?branch=master)](https://travis-ci.com/NickDemianchuk/DS-Team-B)

By Mykola Demianchuk & Robert Kania 

## Functions
The project supports the following:
- Adding/removing/listing togglable devices, eg outlet (true|false)
- Adding/removing/listing dimmable devices, eg dimmable lamp (0-100)
- Adding/removing/listing groups
- Adding/removing devices to groups
- Turning devices on/off/toggling them
- Turning groups on/off/toggling them (runs the operation on all members of the group)
- Creating daily/weekly repeatable and one-time schedules for any supported operation on devices
- Creating and handling events on devices

## Pre-configuration
The project comes pre-initialized with the following devices
- Device Outlet1
  - off
- Device Outlet2
  - off
- Device Outlet3
  - off
  
- Group PowerSwitch1
  - contains Outlet1, Outlet2 and Outlet3
  
## Examples

### List all devices:
```
Choose a command, help for list of commands: listDevices
[DefaultTogglable{id=0, name='Outlet1', isOn=false}, DefaultTogglable{id=1, name='Outlet2', isOn=false}, DefaultTogglable{id=2, name='Outlet3', isOn=false}]
```
### Create a device
```
Choose a command, help for list of commands: createDevice
Device name: NewDeviceName
Type (dimmable|toggle): dimmable
isOn: false
Added device with id 4
```
### Toggle a device
```
Choose a command, help for list of commands: device 0
[Device]Choose a command: status, toggle, on, off, exit: toggle
DefaultTogglable{id=0, name='Outlet1', isOn=true}
```
### List all commands
```
Choose a command, help for list of commands: help
Commands: 
createGroup, createDevice, removeGroup, removeDevice
eventHandlers, listGroups, listDevices
device <id>, group <id>, schedule, exit
```
### Schedule a device to turn off daily at 11 PM
```
Choose a command, help for list of commands: schedule
[Schedule]Choose a command: list, create, remove, exit: create
[Schedule]Name: Turn off device 0 at 11PM
[Schedule]Time (eg. 2009-12-31 23:59:59): 2019-01-27 23:00:00
[Schedule]Interval, blank if one-time (daily|weekly): daily
[Schedule]DeviceId: 0
[Schedule]Task (toggle|turnOn|turnOff): turnOff
[Schedule]Created task with id 0
```
### Turn a device whose name follows the TimedOutlet(number) pattern off an hour after it has been turned on
```
Choose a command, help for list of commands: eventHandlers
[EventHandler]Choose a command: list, create, remove, exit: create
[EventHandler]Name for handler: Turn timed outlet off after one hour
[EventHandler]Name regex for subject (eg. PowerSwitch(\d+)): TimedOutlet(\d+)
[EventHandler]Task to listen on (turnOn|turnOff): turnOn
[EventHandler]Task to perform (toggle|turnOn|turnOff): turnOff
[EventHandler]Delay in minutes (at least 1): 60
[EventHandler]Created handler with id 5
```
### Add a device to a group
```
Choose a command, help for list of commands: listDevices
[DefaultTogglable{id=0, name='Outlet1', isOn=true}, DefaultTogglable{id=1, name='Outlet2', isOn=false}, DefaultTogglable{id=2, name='Outlet3', isOn=false}, DefaultDimmable{id=4, name='NewDeviceName', dimLevel=0}]
Choose a command, help for list of commands: listGroups
[DeviceGroup{id='3', name='PowerSwitch1', size=3}]
Choose a command, help for list of commands: group 3
[Group]Choose a command: addDevice <id>, removeDevice <id>, list, toggle, exit: addDevice 4
[DefaultTogglable{id=0, name='Outlet1', isOn=true}, DefaultTogglable{id=1, name='Outlet2', isOn=false}, DefaultTogglable{id=2, name='Outlet3', isOn=false}, DefaultDimmable{id=4, name='NewDeviceName', dimLevel=0}]
```
### Toggling all devices in a group
```
Choose a command, help for list of commands: group 3
[Group]Choose a command: addDevice <id>, removeDevice <id>, list, toggle, exit: toggle
[DefaultTogglable{id=0, name='Outlet1', isOn=false}, DefaultTogglable{id=1, name='Outlet2', isOn=true}, DefaultTogglable{id=2, name='Outlet3', isOn=true}, DefaultDimmable{id=4, name='NewDeviceName', dimLevel=100}]
[Group]Choose a command: addDevice <id>, removeDevice <id>, list, toggle, exit: toggle
[DefaultTogglable{id=0, name='Outlet1', isOn=true}, DefaultTogglable{id=1, name='Outlet2', isOn=false}, DefaultTogglable{id=2, name='Outlet3', isOn=false}, DefaultDimmable{id=4, name='NewDeviceName', dimLevel=0}]
```
