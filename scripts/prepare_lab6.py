"""Prepare an isolated Tomcat 9 instance using the published lab6 configuration."""
import argparse
import os
from pathlib import Path
import shutil


def main():
    root = Path(__file__).resolve().parents[1]
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--tomcat-home', default=os.environ.get('CATALINA_HOME'))
    parser.add_argument('--base', type=Path, default=root / 'local/lab6-server')
    args = parser.parse_args()
    if not args.tomcat_home:
        parser.error('Set --tomcat-home or CATALINA_HOME')
    home = Path(args.tomcat_home).resolve()
    base = args.base.resolve()
    war = root / 'lab6/target/lab6.war'
    if not (home / 'conf/web.xml').is_file():
        parser.error('Tomcat installation must contain conf/web.xml')
    if not war.is_file():
        parser.error('Build lab6 first: mvn -f lab6/pom.xml package')
    if base == home:
        parser.error('The isolated base must differ from the Tomcat installation')
    for name in ('conf', 'logs', 'temp', 'webapps', 'work'):
        (base / name).mkdir(parents=True, exist_ok=True)
    shutil.copytree(home / 'conf', base / 'conf', dirs_exist_ok=True)
    for name in ('server.xml', 'tomcat-users.xml', 'localhost.p12'):
        shutil.copy2(root / 'lab6/config' / name, base / 'conf' / name)
    shutil.copy2(root / 'lab6/config/credentials.json', base / 'credentials.json')
    shutil.copy2(war, base / 'webapps/lab6.war')
    print(f'Prepared CATALINA_BASE: {base}')
    print('Start Tomcat with this base; open https://localhost:8446/lab6/')


if __name__ == '__main__':
    main()
