from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from bs4 import BeautifulSoup
import time
import urllib.request
import re
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait as wait

driver = webdriver.Chrome('..\\chromedriver.exe')
url = 'https://twitter.com/vegan_recipe_'
driver.get(url)

link_xpath='//*[@id="react-root"]/div/div/div[2]/main/div/div/div/div/div/div[2]/div/div/div[2]/section/div/div/div[3]'
wait(driver, 10).until(EC.frame_to_be_available_and_switch_to_it(driver.find_element_by_xpath(link_xpath)))