from pathlib import Path
import secrets,json,xml.etree.ElementTree as E
base=Path('tools/lab6-server'); pw=secrets.token_urlsafe(18); store=secrets.token_urlsafe(18)
(base/'credentials.json').write_text(json.dumps({'username':'student','password':pw,'storepass':store,'guest':'guest','guestpassword':secrets.token_urlsafe(18)}))
w=Path('lab6/src/main/webapp/WEB-INF/web.xml'); t=w.read_text(encoding='utf-8-sig');t=t.replace('</web-app>','''<security-constraint><web-resource-collection><web-resource-name>Orders</web-resource-name><url-pattern>/*</url-pattern></web-resource-collection><auth-constraint><role-name>order-user</role-name></auth-constraint></security-constraint><login-config><auth-method>BASIC</auth-method><realm-name>Lab6 Orders</realm-name></login-config><security-role><role-name>order-user</role-name></security-role></web-app>''');w.write_text(t,encoding='utf-8')
c=json.loads((base/'credentials.json').read_text()); users=E.Element('tomcat-users'); E.SubElement(users,'role',rolename='order-user');E.SubElement(users,'role',rolename='guest');E.SubElement(users,'user',username='student',password=pw,roles='order-user');E.SubElement(users,'user',username='guest',password=c['guestpassword'],roles='guest');E.ElementTree(users).write(base/'conf/tomcat-users.xml',encoding='utf-8',xml_declaration=True)
p=base/'conf/server.xml'; tree=E.parse(p);root=tree.getroot();root.set('port','18006'); service=root.find('Service')
for conn in service.findall('Connector'):service.remove(conn)
E.SubElement(service,'Connector',{'port':'8086','address':'127.0.0.1','protocol':'HTTP/1.1','redirectPort':'8446','useBodyEncodingForURI':'true'})
con=E.SubElement(service,'Connector',{'port':'8446','address':'127.0.0.1','protocol':'org.apache.coyote.http11.Http11NioProtocol','SSLEnabled':'true','scheme':'https','secure':'true','useBodyEncodingForURI':'true'})
h=E.SubElement(con,'SSLHostConfig',{'protocols':'TLSv1.2,TLSv1.3'});E.SubElement(h,'Certificate',{'certificateKeystoreFile':str((base/'conf/localhost.p12').resolve()),'certificateKeystorePassword':store,'certificateKeystoreType':'PKCS12','type':'RSA'})
tree.write(p,encoding='utf-8',xml_declaration=True)
