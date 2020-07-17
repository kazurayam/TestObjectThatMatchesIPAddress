<?xml version="1.0" encoding="UTF-8"?>
<WebElementEntity>
   <description></description>
   <name>NumbersAndNonconsecutiveDotsOf3</name>
   <tag></tag>
   <elementGuidId>12cc7f4b-d7b8-400d-9185-d8e5f7b46dc9</elementGuidId>
   <selectorCollection>
      <entry>
         <key>BASIC</key>
         <value>//li[
    (string-length(normalize-space(translate(text(), '.0123456789', ''))) = 0) and 
    (string-length(text()) - string-length(translate(text(), '.', '')) = 3 ) and
    not(contains(text(), '..'))
]</value>
      </entry>
   </selectorCollection>
   <selectorMethod>BASIC</selectorMethod>
   <useRalativeImagePath>false</useRalativeImagePath>
   <webElementProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>xpath</name>
      <type>Main</type>
      <value>//li[
    (string-length(normalize-space(translate(text(), '.0123456789', ''))) = 0) and 
    (string-length(text()) - string-length(translate(text(), '.', '')) = 3 ) and
    not(contains(text(), '..'))
]</value>
   </webElementProperties>
</WebElementEntity>
