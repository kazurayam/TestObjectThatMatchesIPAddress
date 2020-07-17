import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.nio.file.Path
import java.nio.file.Paths

import org.openqa.selenium.WebElement

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path fixtureDir = projectDir.resolve('Include/fixture')
Path html = fixtureDir.resolve('aut.html')
WebUI.comment("html=${html.toString()}")
URL aut = html.toFile().toURI().toURL()

List<TestObject> testObjects = [
	findTestObject('DotAndNumbersOnly')
]

WebUI.openBrowser(aut.toString())
for (tObj in testObjects) {
	applyTestObject(tObj)
}
WebUI.closeBrowser()

def applyTestObject(TestObject tObj) {
	WebUI.comment("tObj=${getLocatorOf(tObj)}")
	List<WebElement> matchedElements = WebUI.findWebElements(tObj, 3)
	if (matchedElements.size() > 0) {
		WebUI.comment("================================================================================")
		WebUI.comment("XPath expression \"${getLocatorOf(tObj)}\" matched :")
		for (e in matchedElements) {
			def text = WebUI.getText(tObj)
			WebUI.comment("${text}")
		}
	} else {
		WebUI.comment("XPath expression \"${getLocatorOf(tObj)}\" had no match")
	}
}

String getLocatorOf(TestObject tObj) {
	String locator
	switch (tObj.getSelectorMethod()) {
		case SelectorMethod.BASIC:
			locator = tObj.getSelectorCollection()[SelectorMethod.BASIC]
		case SelectorMethod.CSS:
			locator = tObj.getSelectorCollection()[SelectorMethod.CSS]
		case SelectorMethod.XPATH:
			locator = tObj.getSelectorCollection()[SelectorMethod.XPATH]
		default:
			throw new IllegalArgumentException("unexpected SelectorMethod: ${tObj.getSelectorMethod()}")
	}
	if (locator == null) {
		throw new IllegalStateException("locator is null")
	}
	return locator
}
