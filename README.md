# SmartFit AI - Workout Service

Mikrostoritev za upravljanje vadbenih načrtov in aktivnosti aplikacije SmartFit AI.

## O projektu

SmartFit AI je fitnes aplikacija, ki uporabnikom omogoča sledenje vadbenim aktivnostim in s pomočjo umetne inteligence generira personalizirane vadbene načrte. Ta mikrostoritev skrbi za upravljanje vadbenih načrtov, vaj in sledenje napredku.

## Začetek

### Predpogoji
- Java 17 (LTS)
- Maven 3.6+

### Gradnja

```bash
mvn clean package
```

### Zagon

```bash
java -jar target/workout-service-1.0.0-SNAPSHOT.jar
```

Storitev bo dostopna na `http://localhost:8081`

## API

- `GET /v1/workouts/health` - Preverjanje stanja storitve
- `GET /v1/workouts` - Informacije o storitvi

## Povezani repozitoriji

- [Frontend](https://github.com/prpo-smartfit-ai/frontend)
- [Storitev za uporabnike](https://github.com/prpo-smartfit-ai/user-service)
- [AI storitev](https://github.com/prpo-smartfit-ai/ai-service)

## Licenca

MIT