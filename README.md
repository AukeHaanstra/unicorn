# Unicorn Backend Web MVC Application examples illustrating Hexagonal Architecture and Domain Driven Design

This repository shows a three-layer architecture using best-practices for decoupling of its layers. It also shows
an adapter pattern to decouple a bounded context. This application can be readily refactored to hexagonal architecture
as is shown in the unicorn-hexagonal repository. Java modules can then be easily introduced as shown in the
unicorn-modules repository.

For example requests to the Web API, see UnicornAppRequests.postman_collection.json

## Class diagram

Simplified class diagram for the unicorn bounded context:

```mermaid
classDiagram
    direction TB
    UnicornController..>UnicornService
    UnicornService..>UnicornRepository
    UnicornLegController..>UnicornLegService
    UnicornLegService..>UnicornRepository

    class UnicornController {
        -GetUnicornService
        +getUnicorn(unicornId)
    }
    class UnicornLegController {
        -UnicornLegService
        +getLeg(unicornId, legPosition)
        +patchLeg(unicornId, legPosition, jsonPatch)
    }
    class UnicornService {
        -UnicornRepository
        +getUnicorn(unicornId)
    }
    class UnicornLegService {
        -UnicornRepository
        +getLeg(queryLegDto)
        +updateLeg(updateLegDto)
    }
    class UnicornRepository {
        +find(unicornId)
        +update(unicorn)
    }
```

Class diagram showing enrichment from the finance bounded context:

```mermaid
classDiagram
    direction TB
    UnicornController..>UnicornService
    UnicornService..>UnicornRepository
    UnicornLegController..>UnicornLegService
    UnicornLegService..>UnicornRepository

    UnicornService..>UnicornEnrichmentService
    UnicornEnrichmentService..>FinancialHealthApi
    FinancialHealthAdapter..>FinancialHealthApi
    FinancialHealthAdapter..>SalesService

    namespace Unicorn {
        class UnicornController {
            -GetUnicornService
            +getUnicorn(unicornId)
        }
        class UnicornLegController {
            -UnicornLegService
            +getLeg(unicornId, legPosition)
            +patchLeg(unicornId, legPosition, jsonPatch)
        }
        class UnicornService {
            -UnicornRepository
            -UnicornEnrichmentService
            +getUnicorn(unicornId)
        }
        class UnicornLegService {
            -UnicornRepository
            +getLeg(queryLegDto)
            +updateLeg(updateLegDto)
        }
        class UnicornRepository {
            +find(unicornId)
            +update(unicorn)
        }
        class UnicornEnrichmentService {
            -FinancialHealthApi
            +enrich(unicorn)
        }
        class FinancialHealthApi {
            +getFinancialHealth(unicornId)
        }
    }
    namespace Finance {
        class SalesService {
            +calculateTotalSales(unicornId)
        }
    }
    namespace Adapter {
        class FinancialHealthAdapter {
            -SalesService
            +getFinancialHealth(unicornId)
        }
    }
```

Instead of the regular depedency:

```mermaid
classDiagram
    direction TB
    Upstream-->Downstream
    Upstream..>Downstream

    class Upstream {
        -Downstream
        +doSomething()
    }
    class Downstream {
        +doSubtask()
    }
```

We make use of dependency inversion to decouple the two bounded contexts:

```mermaid
classDiagram
    direction LR
    Upstream-->Interface
    Upstream..>Interface
    Downstream..>Interface

    class Upstream {
        -Downstream
        +doSomething()
    }
    class Interface {
        +doSubtask()
    }
    class Downstream {
        +doSubtask()
    }
```

Test-per-class testing:

```mermaid
flowchart LR
    UnicornIT --> ControllerPair
    subgraph ControllerPair
        direction TB
        UnicornControllerTest --> UnicornController
    end
    subgraph ServicePair
        direction TB
        UnicornServiceTest --> UnicornService
    end
    ControllerPair --> ServicePair
    ServicePair --> UnicornPair
    subgraph Domain Model
        direction LR
        UnicornPair --> LegPair
        subgraph UnicornPair
            direction TB
            UnicornTest --> Unicorn
        end
        subgraph LegPair
            direction TB
            LegTest --> Leg
        end

    end
    ServicePair --> H2/testcontainer
    classDef red stroke:#f00;
    class UnicornControllerTest red;
    class UnicornServiceTest red;
    class UnicornTest red;
    class LegTest red;
    classDef green stroke:#0f0
    class UnicornIT green;
    class H2/testcontainer green;
```

Api testing:

```mermaid
flowchart TB
    subgraph weblayer[Web-layer]
        direction TB
        UnicornController
    end
    subgraph applayer[Application-layer]
        subgraph domain[domain model]
            direction LR
            Unicorn --> Leg
        end
        UnicornService --> domain
    end
    subgraph persistencelayer[Persistence-layer]
        direction TB
        InMemoryUnicornRepo -. dependency .-> UnicornRepo
        H2/testcontainer -. dependency .-> UnicornRepo
    end
    ApiTest --> applayer
    weblayer --> applayer
    applayer --> persistencelayer
    UnicornIT --> weblayer
    WebLayerTest --> weblayer
    PersistenceLayerTest --> UnicornRepo
    weblayer --> UnicornServiceMock
    classDef red stroke:#f00;
    classDef green stroke:#0f0;
    classDef orange stroke:#ffa500;
    class ApiTest red;
    class UnicornIT green;
    class H2/testcontainer green;
    class InMemoryUnicornRepo red;
    class ColoringApi red;
    class UnicornService red;
    class WebLayerTest orange;
    class PersistenceLayerTest orange;
    class UnicornServiceMock orange;
```

Testing with Hexagonal Architecture:

```mermaid
flowchart TB
    subgraph webadapter[Web-adapter]
        direction TB
        UnicornController
    end
    subgraph application[Application-core]
        subgraph domain[domain model]
            direction LR
            Unicorn --> Leg
        end
        UnicornService -. dependency .-> UnicornUsecase
        UnicornService --> domain
    end
    subgraph persistenceadapter[Persistence-adapter]
        direction TB
        InMemoryUnicornRepo -. dependency .-> UnicornRepo
        H2/testcontainer -. dependency .-> UnicornRepo
    end
    UsecaseTest --> application
    webadapter --> application
    application --> persistenceadapter
    UnicornIT --> webadapter
    WebAdapterTest --> webadapter
    PersistenceAdapterTest --> UnicornRepo
    webadapter --> UnicornServiceMock
    classDef red stroke:#f00;
    classDef green stroke:#0f0;
    classDef orange stroke:#ffa500;
    class UnicornUsecase red;
    class UsecaseTest red;
    class UnicornIT green;
    class H2/testcontainer green;
    class InMemoryUnicornRepo red;
    class ColoringApi red;
    class WebAdapterTest orange;
    class PersistenceAdapterTest orange;
    class UnicornServiceMock orange;
```

## Sources

- Get Your Hands Dirty on Clean Architecture, Tom Hombergs
- https://docs.aws.amazon.com/prescriptive-guidance/latest/hexagonal-architectures/welcome.html (ook in PDF formaat)
- Migrating your Spring Boot application to Java Modules by Jaap Coomans – YouTube (presentatie @ Devoxx)
- https://cleancoders.com/library/all (Clean Code series videos door Robert C. Martin, aka Uncle Bob)
- Agile Software Development: Principles, Patterns and Practices, Robert C. Martin (aka Uncle Bob)
- The Java Module System, Nicolai Parlog
- https://github.com/TomCools/jpms-hexagonal-architecture (voorbeeld project door Tom Cools)