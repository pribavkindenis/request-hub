dev:
	@docker-compose -f docker-compose.yml -f docker-compose.dev.yml up

down-dev:	
	@docker-compose -f docker-compose.yml -f docker-compose.dev.yml down

rm-dev:
	@docker-compose -f docker-compose.yml -f docker-compose.dev.yml down -v

prod:
	@docker-compose -f docker-compose.yml -f docker-compose.prod.yml up

down-prod:
	@docker-compose -f docker-compose.yml -f docker-compose.prod.yml down

rm-prod:
	@docker-compose -f docker-compose.yml -f docker-compose.prod.yml down -v
