from selenium import webdriver
import time
import re
from bs4 import BeautifulSoup
import unicodedata
import requests

def move_next(driver):
    right = driver.find_element_by_css_selector('a._65Bje.coreSpriteRightPaginationArrow')
    right.click()
    time.sleep(3)

driver = webdriver.Chrome() #크롬을 이용
word = '비건레시피'
url = 'https://www.instagram.com/explore/tags/' + word
driver.get(url)
time.sleep(2) #검색페이지열기

login_section = '//*[@id="react-root"]/section/nav/div[2]/div/div/div[3]/div/span/a[1]/button'
driver.find_element_by_xpath(login_section).click()
time.sleep(3)

elem_login = driver.find_element_by_name("username")
elem_login.clear()
elem_login.send_keys('vegan_recipe_test')

elem_login = driver.find_element_by_name('password')
elem_login.clear()
elem_login.send_keys('test1234')

time.sleep(2)

xpath = """//*[@id="loginForm"]/div/div[3]/button/div"""
driver.find_element_by_xpath(xpath).click()

time.sleep(4)

xpath1 = """//*[@id="react-root"]/section/main/div/div/div/div/button"""
driver.find_element_by_xpath(xpath1).click()
time.sleep(4)

g = open('instatext.txt', 'w', encoding='utf-8')
f = open('instatext.csv', 'w',  encoding='utf-8')

html = driver.page_source
soup = BeautifulSoup(features="html.parser")

firstinstapost = driver.find_element_by_css_selector('div._9AhH0')
#instaurl = driver.find_element_by_css_selector('#react-root > section > main > article > div.EZdmt > div > div > div > div > a')
instaurl = driver.find_element_by_xpath('//*[@id="react-root"]/section/main/article/div/div/div/div/div[1]/a').get_attribute('href')
instaimgurl = driver.find_element_by_xpath('//*[@id="react-root"]/section/main/article/div/div/div/div/div/a/div/div/img').get_attribute('src')
firstinstapost.click() #첫번째 게시글 열기
time.sleep(2)

number = 1

for i in range(400):
    html = driver.page_source
    soup = BeautifulSoup(features="html.parser")

    g.write("#"+str(number)+"\n")
    f.write("#"+str(number))
    print("#"+str(number)+"\n")

    # 본문 내용 가져오기
    try:  # 여러 태그중 첫번째([0]) 태그를 선택
        content = driver.find_element_by_css_selector('div.C4VMK > span').text  # 첫 게시글 본문 내용이 <div class="C4VMK"> 임을 알 수 있다.
        content_edit = unicodedata.normalize('NFC', content)
        # 태그명이 div, class명이 C4VMK인 태그 아래에 있는 span 태그를 모두 선택.
    except:
        content = ' '

    # 본문 내용에서 해시태그 가져오기(정규표현식 활용)
    tags = re.findall(r'#[^\s#,\\]+', content)

    for j in range(len(tags)):
        tags_edit = unicodedata.normalize('NFC', tags[j])
        g.write(tags_edit)
        f.write(tags_edit)
        print(tags_edit)
    g.write('\n')

    print(instaurl+'\n')
    g.write(instaurl+'\n')
    f.write(instaurl)
    
    print(instaimgurl+'\n')
    g.write(instaimgurl+'\n')
    f.write(instaimgurl)

    g.write(content_edit+'\n')
    f.write(content_edit+'\n')
    print(content_edit+'\n')

    move_next(driver)
    number += 1
    if(number%50==0):
        time.sleep(30)

g.close()
f.close()
driver.close()
time.sleep(1)
print("End")
