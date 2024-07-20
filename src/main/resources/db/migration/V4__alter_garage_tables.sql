ALTER TABLE garages ADD COLUMN mileage INTEGER;
ALTER TABLE garages ADD COLUMN acceleration INTEGER;
ALTER TABLE garages ADD COLUMN top_speed INTEGER;
ALTER TABLE garages ADD COLUMN engine_power INTEGER;
ALTER TABLE garages ADD COLUMN torque INTEGER;
ALTER TABLE garages ADD COLUMN transmission_type VARCHAR(256);
ALTER TABLE garages ADD COLUMN fuel_type VARCHAR(256);

ALTER TABLE vehicle_garages ADD COLUMN engine_position VARCHAR(256);
ALTER TABLE vehicle_garages ADD COLUMN engine_layout VARCHAR(256);
ALTER TABLE vehicle_garages ADD COLUMN vehicle_engine_aspiration VARCHAR(256);
ALTER TABLE vehicle_garages ADD COLUMN drive_train VARCHAR(256);

ALTER TABLE vehicle_garages DROP COLUMN vehicle_mileage;
ALTER TABLE vehicle_garages DROP COLUMN vehicle_transmission;
ALTER TABLE vehicle_garages DROP COLUMN vehicle_fuel_type;

ALTER TABLE motorbike_garages ADD COLUMN motorbike_engine_aspiration VARCHAR(256);

ALTER TABLE motorbike_garages DROP COLUMN motorbike_mileage;
ALTER TABLE motorbike_garages DROP COLUMN motorbike_transmission;
ALTER TABLE motorbike_garages DROP COLUMN motorbike_fuel_type;