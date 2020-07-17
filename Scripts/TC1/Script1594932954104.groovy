import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.nio.file.Path
import java.nio.file.Paths

import org.openqa.selenium.WebElement

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.TestObjectBuilder
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kazurayam.ks.testobject.TestObjectExtension

TestObjectExtension.apply()

Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path fixtureDir = projectDir.resolve('Include/fixture')
Path html = fixtureDir.resolve('aut.html')
WebUI.comment("html=${html.toString()}")
URL aut = html.toFile().toURI().toURL()

List<TestObject> testObjects = [
	findTestObject('NumbersAndDots'),
	findTestObject('NumbersAndDotsOf3'),
	findTestObject('NumbersAndNonconsecutiveDotsOf3')
]

WebUI.openBrowser(aut.toString())
for (tObj in testObjects) {
	// println tObj.prettyPrint()
	applyTestObject(tObj)
}
WebUI.closeBrowser()


def applyTestObject(TestObject tObj) {
	
	List<WebElement> matchedElements = WebUI.findWebElements(tObj, 10)
	
	WebUI.comment("================================================================================")
	if (matchedElements.size() > 0) {
		WebUI.comment("XPath expression \"${getLocatorOf(tObj)}\" matched :")
		for (e in matchedElements) {
			def content = e.getText()
			WebUI.comment(">${content}")
		}
	} else {
		WebUI.comment("XPath expression \"${getLocatorOf(tObj)}\" had no match")
	}
}


/**
 * get XPath locator of a TestObject
 * @param tObj
 * @return
 */
String getLocatorOf(TestObject tObj) {
	String locator
	switch (tObj.getSelectorMethod()) {
		case SelectorMethod.BASIC:
			locator = tObj.getSelectorCollection()[SelectorMethod.BASIC]
			break
		case SelectorMethod.CSS:
			locator = tObj.getSelectorCollection()[SelectorMethod.CSS]
			break
		case SelectorMethod.XPATH:
			locator = tObj.getSelectorCollection()[SelectorMethod.XPATH]
			break
		default:
			throw new IllegalArgumentException("unexpected SelectorMethod: ${tObj.selectorMethod}")
	}
	if (locator == null) {
		throw new IllegalStateException("locator is null")
	}
	return locator
}
