Katalon TestObject that matches IP Address
============

This is a small Katalon Stduio project for demonstration purpse. You can download the zip from the REALSES page, unzip it and open it with your Katalon Studio.

This project is developed using Katalon Studio version 7.5.5.

# Problem to solve

Let me suppose I have a web page with following HTML fragment:

```
  <ul>
    <li>abc</li>
    <li>de.f.ghi.j</li>
    <li>1.23.45.67</li>
    <li>9.8.7.6.5.4.3.2.1</li>
    <li>hij 2.3.4.5 klm</li>
    <li>      6.7.8.9      </li>
    <li>0123456789...</li>
  </ul>
```

Some of `<li>` element contains String of IP Address-like format. When I say *IP Address-like* means a string in dot-decimal notation, consisting of four decimal numbers, each ranging from 0 to 255, separated by dots. A IP Address-liken string should match with a Java-flavor Regular Expression `\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}`.

Now I want to write a Test Case script in Katalon Studio. My scrit need to be able to select the `<li>` elements containing `IP Address-like` string. I want to use regular TestObjects with XPath expression locator.

So how should I write XPath expression to select `<li>` elements containing IP Address?

# Solution

Web browers does not implement XPath 2.0. Therefore I can not use Regular expression in XPath in TestObject. I should write locator using XPath 1.0 built-in functions only. Is it possible? --- Yes, I can, though the solution is far from elegance.

# Description

I wrote a test case script [TC1](Scripts/TC1/Script1594932954104.groovy).

I wrote 3 TestObject instances:
- [NumbersAndDots](Object Repository/NumbersAndDots.rs)
- [NumbersAndDotsOf3](Object Repository/NumbersAndDotsOf3.rs)
- [NumbersAndNonconsecutiveDotsOf3](Object Repository/NumbersAndNonconsecutiveDotsOf3.rs)

TC1 opens [aut.html](Include/fixture/aut.html) and applies above 3 TestObject trying to select `<li>` elements of interest.

## `NumbersAndDots`

This test object is assigned with a XPath like this:

```
//li[string-length(normalize-space(translate(text(), '.0123456789', ''))) = 0]
```

Let me try to dectate what this XPath expression does.

1. it selects `li` elements which 
2. has a content string of a dot (.), numbers(0..9) and whitespaces
3. hos no characters other than dot, numbers and whitespaces

When I execute this, I could see in the Console that `NumbersAndDots` selected the following elements:

```
1.23.45.67
9.8.7.6.5.4.3.2.1
6.7.8.9
0123456789...
```

## `NumbersAndDotsOf3`

2nd Test object is assigned with the following XPath expression:

```
//li[
    (string-length(normalize-space(translate(text(), '.0123456789', ''))) = 0) and 
    (string-length(text()) - string-length(translate(text(), '.', '')) = 3 )
    ]
```

This expression has second condition. It requires the content text should contain exactly 3 dot characters.

When I execute the test, I could see in the console `NumbersAndDotsOf3` selected the following elements:

```
1.23.45.67
6.7.8.9
0123456789...
```

## `NumbersAndNonconsecutiveDotsOf3`

3rd test object has following XPath expression assigned:

```
//li[
    (string-length(normalize-space(translate(text(), '.0123456789', ''))) = 0) and 
    (string-length(text()) - string-length(translate(text(), '.', '')) = 3 ) and
    not(contains(text(), '..'))
]
```

This expression has 3rd condition, which requires the context text should not contain consecutive 2 dot characters to apprear.

When I run the test I could see in the console `NumbersAndNonconsecutiveDotsOf3` selected the following elements:

```
1.23.45.67
6.7.8.9
```

My problem is solved.

