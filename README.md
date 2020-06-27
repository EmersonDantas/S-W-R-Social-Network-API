# Star Wars Resistence Social Network API
## To access full documentation: Start the API and enter in http://localhost:8080/swagger-ui.html

* **Explicação sobre a negociação de itens**

Para negociar itens entre dois rebeldes é preciso que um **rebelde A** possua os **items A** no seu inventário(lista de itens).
O mesmo vale para o **rebelde B** e seus itens.
Se ambos os rebeldes não forem traidores da resistência, e os pontos da troca forem equivalentes, a troca será feita.

* Exemplo de troca:

Supondo que o **rebelde A** de **id 1** possua os **itemsFrom** e o **rebelde B** de **id 2** possua os **itemsTo**, essa é será uma troca válida.

Para essa troca, deve-se usar o endpoint **/api/v1/rebels/negotiate-items/1/2**

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
