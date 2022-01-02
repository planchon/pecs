# pECS

A Super simple and elegant ECS system for Java. 

## ECS Explained
ECS is a program architecture made to transform higly vertical code (using a lot of heritage)
into horizontal code. ECS is highly cached optimized (data that will be used in the short term is nearer other data,
even in the "Entity").

New coding practices should be focus on writting more vertical code using less and less inheritance.

The future is **data oriented coding**.

## Fonctionnalities
 - [x] Pure Java, no libraries needed
 - [x] Fast and optimized for speed (sort of, need to benchmark)
 - [ ] High level system running on differents ticks speed
 - [ ] Built-in profiler

## Usage
*In `/tests/Simple.java` you can find a simple example of an implementation of an ECS*

### Engine

pECS is centered around the Engine. The engine is what make the ECS running. Its responsible for Component, Entities and System 
data and memory allocation.
```java
Engine engine = new Engine();
```

### Systems
A system is responsible for all the calculation. A system can have sides effects, but their must be contained over
the Components the system has view of.
```java
public static class MovementSystem extends ECSSystem {
    public ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {}

    @Override
    public void removedFromEngine(Engine engine) {}

    @Override
    public void update(double dt) {}
}
```

When you create a System, you have to specifies for optimization which components the system will be using during `update`.
```java
public void addedToEngine(Engine engine) {
    entities = engine.getEntitiesFor(Family.all(
            PositionComponent.class, 
            MovementComponent.class
    ).get());
}
```
`entities` is immutable so dont worry about entity allocation. The engine takes care of that.
By telling the engine which components the system will be using at runtime, the engine can cache entities in the good families.

### Components 
Components are just data holder. No functions should be implemented in `Components`. Creating a component is very simple : 

```java
public class MovementComponent implements Component {
	public float vx, vy;
	
	public MovementComponent(float vx, float vy) {
		this.vx = vx;
		this.vy = vy;
	}
}
```

Again, components are made only to hold data. So only constructor and public data accessors are requiered.

### Entities
Entities are the glue between `Systems` and `Components`. An entity is unique and is made of `components`. 
```java
Entity entity = engine.createEntity();
entity.addComponent(new MovementComponent(i, 0));
engine.addEntity(entity);
```

### Starting and running the ECS
The library is not using a loop of any kind. You should maybe implement a 60Hz loop to update your systems but pECS dont do that.
To update all the system just call : 
```java
double dt = 1.f;
engine.update(dt);
```

## Credits
pECS is very inspired by [Ashley](https://github.com/libgdx/ashley) codebase. 

Under Apache Licence.
