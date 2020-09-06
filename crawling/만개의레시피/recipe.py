from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from bs4 import BeautifulSoup
import time
 
path ="C:\\Users\\hailie\\Desktop\\last\\chromedriver.exe"
driver = webdriver.Chrome(path)

number = 1
g = open("recipe.txt",'w',encoding='utf-8')
for n in range(1,5) :
    site = 'https://www.10000recipe.com/recipe/list.html?q=%EC%B1%84%EC%8B%9D&order=reco&page='
    site = site + str(n)
    driver.get(site)
    sample = driver.find_elements_by_css_selector('.common_sp_link')
    for i in range(len(sample)) :
        driver.get(site)
        #레시피 링크
        findurl = driver.find_elements_by_css_selector('.common_sp_link')[i].get_attribute('href')
        driver.get(findurl)
        #레시피 이름
        foodName = driver.find_elements_by_xpath('//*[@id="contents_area"]/div[2]/h3')
        #레시피 사진
        foodImage = driver.find_element_by_xpath('//*[@id="main_thumbs"]').get_attribute('src')
        g.write(foodName[0].text+'\t'+findurl+'\t'+foodImage)
        g.write('\n')
    
g.close()
