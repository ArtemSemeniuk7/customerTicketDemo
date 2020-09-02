# customerTicketDemo
Задание мне очень понравилось, с удовольствием решил. Постарался решить все пункты.
Для запуска проекта нужно скачать mysql драйвер и создать базу данных customer_data_base, 
также нужно иметь свободным 8080 порт.


Сваггер доокументация:

![alt text](https://github.com/ArtemSemeniuk7/customerTicketDemo/blob/master/swagger.png)


Структура проекта:

![alt text](https://github.com/ArtemSemeniuk7/customerTicketDemo/blob/master/project.png)


POST метод RequestRestController для сохранения заявки в базу данных

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

GET метод RequestRestController выдает все заявки для данного клиента, дата которых в будущем

![alt text](https://github.com/ArtemSemeniuk7/customerTicketDemo/blob/master/request-get-future-data%7Bid%7D.png)

результат выполнения:

https://github.com/ArtemSemeniuk7/customerTicketDemo/blob/master/get-future-data.json
