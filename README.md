# customerTicketDemo
Задание мне очень понравилось, с удовольствием решил. Постарался выполнить все пункты.

Для запуска проекта нужно скачать mysql драйвер и создать базу данных customer_data_base, 
также нужно иметь свободным 8080 порт.


Сваггер доокументация:

![alt text](https://github.com/ArtemSemeniuk7/customerTicketDemo/blob/master/swagger.png)



Структура проекта:


![alt text](https://github.com/ArtemSemeniuk7/customerTicketDemo/blob/master/project.png)



POST метод RequestRestController для сохранения заявки в базу данных. На вход принимает JSON, на выход отдает id заявки.



![alt text](https://github.com/ArtemSemeniuk7/customerTicketDemo/blob/master/request-post.png)

запрос для проверки
{
  "clientId": 3,
  "date": "2020-09-02 08:23:07",
  "executionStatus": "string",
  "requestId": 0,
  "requestStatus": 0,
  "routeNumber": 32
}


GET метод RequestRestController выдает все заявки дата которых в будущем, для данного клиента, на вход принимает id клиента, отдает все заявки данного клиента у которых дата в будущем.


![alt text](https://github.com/ArtemSemeniuk7/customerTicketDemo/blob/master/request-get-future-data%7Bid%7D.png)

результат выполнения:
https://github.com/ArtemSemeniuk7/customerTicketDemo/blob/master/get-future-data.json


RequestIDRestController отправляет заявки в StatusRestController, который генерирует случайную заявку. На вход принимает id заявки, указанный в URI, на выход отдает http статус.

В RequestIDRestController каждую минуту отправляет запрос CronJob, что бы провести еще раз попытку оплаты заявок со статусами 307 и 0.

До работы CronJob:


![alt text](https://github.com/ArtemSemeniuk7/customerTicketDemo/blob/master/get-request-check-%7Bid%7D%20before%20cron%20job.png)



Результат работы CronJob:

![alt text](https://github.com/ArtemSemeniuk7/customerTicketDemo/blob/master/get-request-check-%7Bid%7D%20after%20cron%20job.png)


