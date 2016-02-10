using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using OpenQA.Selenium.PhantomJS;
using System.IO;
using System.Drawing.Imaging;

namespace WebDriverDemo
{
    class Program
    {
        static void Main(string[] args)
        {
            String resultPath = @"F:\Google Drive\Presentaciones\Phantom-Selenium\code\webdriver test\results\";
            using (var driver = new FirefoxDriver())
            {
                driver.Navigate().GoToUrl("http://testing-ground.scraping.pro/login");
                
                var userNameField = driver.FindElementById("usr");
                var userPasswordField = driver.FindElementById("pwd");
                var loginButton = driver.FindElementByXPath("//input[@value='Login']");
                
                userNameField.SendKeys("admin");
                userPasswordField.SendKeys("12345");

                
                loginButton.Click();
                
                
                var result = driver.FindElementByXPath("//div[@id='case_login']/h3").Text;
                File.WriteAllText(resultPath + "result.txt", result);

                
                driver.GetScreenshot().SaveAsFile(resultPath + "screen.png", ImageFormat.Png);
            }

        }
    }
}
