import urllib.request,urllib.error,ssl,json,base64
from pathlib import Path
root=Path(__file__).resolve().parent
c=json.loads((root/'config/credentials.json').read_text());ctx=ssl.create_default_context(cafile=str(root/'localhost.crt')); lines=[]
def request(path,user=None,password=None,data=None):
 headers={}
 if user:headers['Authorization']='Basic '+base64.b64encode((user+':'+password).encode()).decode()
 req=urllib.request.Request('https://localhost:8446/lab6/'+path,data=data,headers=headers)
 try:
  with urllib.request.build_opener(urllib.request.ProxyHandler({}),urllib.request.HTTPSHandler(context=ctx)).open(req) as r:return r.status,r.read().decode(),r.headers
 except urllib.error.HTTPError as e:return e.code,e.read().decode(),e.headers
for name,user,pw,code in [('anonymous',None,None,401),('wrong password','student','wrong',401),('wrong role','guest',c['guestpassword'],403),('authorized','student',c['password'],200)]:
 status,body,h=request('index.jsp',user,pw);assert status==code,(name,status);lines.append(f'{name}: HTTPS {status} OK')
 if name=='anonymous':assert 'Basic' in h['WWW-Authenticate']
status,body,_=request('calculate.jsp','student',c['password'],b'customer=Ivan&quantity=3&delivery=courier');assert status==200 and '1050.00' in body;lines.append('authenticated JSP POST: 1050.00 OK')
lines.append('TLS certificate chain and hostname verified against localhost.crt; verification enabled')
(root/'checks.txt').write_text('\n'.join(lines),encoding='utf-8');print('\n'.join(lines))
