package tests;

import core.Component;
import core.Engine;
import systems.ECSSystem;
import core.Entity;
import core.*;
import utils.ImmutableArray;

import java.util.ArrayList;

public class Simple {
	public static class PositionSystem extends ECSSystem {
		public ImmutableArray<Entity> entities;

		@Override
		public void addedToEngine(Engine engine) {
			entities = engine.getEntitiesFor(Family.all(PositionComponent.class).get());
			System.out.println(this.getClass().toString() + "added to the engine");
		}

		@Override
		public void update(double dt) {
		}

		@Override
		public void removedFromEngine(Engine engine) {
			System.out.println("ps remove from engine");
			entities = null;
		}
	}

	public static class MovementSystem extends ECSSystem {
		public ImmutableArray<Entity> entities;

		@Override
		public void addedToEngine(Engine engine) {
			entities = engine.getEntitiesFor(Family.all(PositionComponent.class, MovementComponent.class).get());
			System.out.println(this.getClass().toString() + " added to the engine");
		}

		@Override
		public void removedFromEngine(Engine engine) {
			System.out.println("ps remove from engine");
			entities = null;
		}

		@Override
		public void update(double dt) {
			System.out.println("mouvement system debut");
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);

				PositionComponent p = e.getComponent(PositionComponent.class);
				MovementComponent m = e.getComponent(MovementComponent.class);

				p.x += dt * m.vx;
				p.y += dt * m.vy;

				System.out.println(p.x + ", " + p.y);
			}
		}
	}

	public static void main(String[] args) {
		Engine engine = new Engine();

		PositionSystem ps = new PositionSystem();
		MovementSystem mv = new MovementSystem();

		engine.addSystem(ps);
		engine.addSystem(mv);

		for (int i = 0; i < 10; i++) {
			Entity entity = engine.createEntity();
			entity.addComponent(new PositionComponent(10, i));
			if (i >= 5) entity.addComponent(new MovementComponent(i, 0));

			engine.addEntity(entity);
		}

		for (int i = 0; i < 10; i++) {
			engine.update(1.f);

			if (i > 5) engine.removeSystem(mv);
		}
	}
}
