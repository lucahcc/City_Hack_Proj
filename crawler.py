from selenium import webdriver
import codecs
import csv
import time
url ='https://finance.yahoo.com/quote/AAPL/history?p=AAPL'
dirver=webdriver.Firefox()
csv_file=open("playlist.csv","w",newline='')
writer=csv.writer(csv_file)
writer.writerow(['topic'])
test=1
dirver.get(url)
time.sleep(2)
data = dirver.find_elements_by_tag_name("tbody")[0];
data= dirver.find_elements_by_tag_name("tr");
for i in range(len(data)):
    title=data[i].find_elements_by_tag_name('td');
    for j in range(len(title)):
        
        print(title[j].text+" ")
    #writer.writerow([title])
nextpage=dirver.find_elements_by_css_selector("a.p.next-page")

csv_file.close()
dirver.close()
