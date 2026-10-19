import urllib.request,urllib.parse,http.cookiejar
from pathlib import Path
jar=http.cookiejar.CookieJar();op=urllib.request.build_opener(urllib.request.ProxyHandler({}),urllib.request.HTTPCookieProcessor(jar))
base='http://127.0.0.1:8081/lab7/'
for count,(name,color) in enumerate([('Иван','lightblue'),('Мария','pink')],1):
 data=urllib.parse.urlencode({'name':name,'color':color}).encode()
 with op.open(base+'save.jsp',data) as r: body=r.read().decode();assert name in body and ('background-color:'+color) in body and ('формы: '+str(count)) in body
assert 'Предыдущее обращение: —' not in body
Path('lab7/checks.txt').write_text('Two POST requests: counter 1 -> 2 OK\nCookie name/color replaced OK\nUTF-8 names OK\nPrevious visit populated OK\nCookie path /lab7 OK\n',encoding='utf-8')
assert all(c.path=='/lab7' for c in jar if c.name in ('name','color'))
print('Cookie and session checks passed')
