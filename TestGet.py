import requests
import shutil

id = input("введите id: ")

r= requests.get("http://mail.ext-it.ru:8080/download", params={"id": id},stream=True)
r.raise_for_status()
r.raw.decode_content = True
with open('get.test.jpg', 'wb') as f:
    shutil.copyfileobj(r.raw, f)
