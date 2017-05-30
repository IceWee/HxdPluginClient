@echo off
if exist jre8 (
    start jre8/bin/javaw -jar HxdPluginClient-1.0.jar
) else (
    java -jar HxdPluginClient-1.0.jar
)
