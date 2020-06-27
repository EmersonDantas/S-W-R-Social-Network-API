# Star Wars Resistence Social Network API
## To access full documentation: Start the API and enter in http://localhost:8080/swagger-ui.html

* **Negociação de itens**

Para negociar itens entre dois rebeldes é preciso que um **rebelde A** possua os **items A** no seu inventário(lista de itens).
O mesmo vale para o **rebelde B** e seus itens.
Se ambos os rebeldes não forem traidores da resistência, e os pontos da troca forem equivalentes, a troca será feita.

* Exemplo de troca:

Supondo que o **rebelde A** de **id 1** possua os **itemsFrom** e o **rebelde B** de **id 2** possua os **itemsTo**, essa é será uma troca válida.

Para essa troca, deve-se mandar uma requisição **PATCH** para **/api/v1/rebels/negotiate-items/1/2** e mandar o seguinte json no corpo da requisição:

```javascript
{
	"itemsFrom":[
		{
			"id": 1,
			"name": "arma",
			"amount": 1,
			"points": 4
		},
		{
			"id": 2,
			"name": "agua",
			"amount": 1,
			"points": 2
		}
	],
	"itemsTo":[
		{
			"id": 3,
			"name": "comida",
			"amount": 6,
			"points": 1
		}
	]
}
```

* **Atualizar localização do rebelde**

Para atualizar a localização do rebelde, deve-se enviar uma requisição **PATCH** para **/api/v1/rebels/location/{id}**, onde {id} deve ser o id do rebelde no qual se deseja atualizar a localização, e o seguinte modelo de json no corpo da requisição(com os dados desejados):

```javascript
{
	"latitude":1.0,
	"longitude":1.0,
	"locationName":"base echo"
}
```

* **Cadastrar um novo rebelde**

Para cadastrar um novo rebelde, basta enviar uma requisição **POST** para **/api/v1/rebels** e enviarno corpo da requisição o rebelde, como no exemplo abaixo.

 ```javascript
{
  "name": "Leia Organa",
  "dateOfBirth": "1956-10-21",
  "genre": "femi",
  "galaxy": "Via Láctea",
  "base": "Base Echo",
  "location": {
    "latitude": 0.0,
    "longitude": 0.0,
    "locationName": "base"
  },
  "items": [
		{
			"name":"arma",
			"amount":1,
			"points":4
		},
		{
			"name":"agua",
			"amount":1,
			"points":2
		}
	]
}
 ```

* **Porcentagem de rebeldes traidores da resistência**

Para obter a porcentagem de rebeldes traidores, basta enviar uma requisição **GET** para **/api/v1/rebels/renegade-percentage**

* **Porcentagem de rebeldes fieis a resistência**

Para obter a porcentagem de rebeldes fieis a resistência, basta enviar uma requisição **GET** para **/api/v1/rebels/rebels-percentage**

* **Quantidade de pontos perdidos por rebeldes traidores**

Para obter a quantidade de pontos de rebeldes traidores que perdidos pela resistência, basta enviar uma requisição **GET** para **/api/v1/rebels/renegades-lost-points**

* **Quantidade mádia de items dos rebelde**

Para obter a quantidade média de items dos rebeldes fieis a resistência, basta enviar uma requisição **GET** para **/api/v1/rebels/items-average**

* **Denunciar um rebelde traidor da resistência**

Para denunciar um rebelde que traiu a resistência, basta enviar uma requisição **PATCH** para **/api/v1/rebels/report-rebel/{id}** onde **id** deve ser o id do rebelde traidor.

* **Obter um determinado rebelde**

Para se obter um determinado rebelde, basta enviar uma requisição **GET** para **/api/v1/rebels/{id}** onde **id** deve ser o id do rebelde que dejesa obter.

* **Listar todos os rebeldes**

Para listar todos os rebeldes, basta enviar uma requisição **GET** para **/api/v1/rebels** (ver na documentação do swagger a personalização da listagem).
