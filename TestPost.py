import requests

multipart_form_data = {
    "file": ("testtt.jpg", open('test.jpg', 'rb')),
    "action": (None, "submit")
}
r = requests.post('http://mail.ext-it.ru:8080/upload', files=multipart_form_data)
print("(respose): ",r.text)

