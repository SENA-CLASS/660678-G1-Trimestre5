-- funciones

select * from akuavida.ejercicio_funciones m;
select abs(m.columna1) from akuavida.ejercicio_funciones m;
select cos(m.c	olumna1) from akuavida.ejercicio_funciones m;
select CEILING(m.columna2) from akuavida.ejercicio_funciones m;
select cos(pi());
select acos(1);

SELECT RAND();

select ascii(m.columna6) from akuavida.ejercicio_funciones m;

select bin(1);
select bin(m.columna1) from akuavida.ejercicio_funciones m;


SELECT ROUND(1.378, 2);

select CHAR_LENGTH("hola mundo");

SELECT DATEDIFF(NOW(),'1492-10-12');

-- selects

select sum(i.costoTotal) from tiendaenlinea.item i where i.Pedido_Factura_idFactura=1; 

delete from tiendaenlinea.item  where Pedido_Factura_idFactura=1 and Producto_idProducto='A0001';

UPDATE `tiendaenlinea`.`pedido`
SET

`total` = (select sum(i.costoTotal) from tiendaenlinea.item i where i.Pedido_Factura_idFactura=10)*1.16,
`subtotal` = (select sum(i.costoTotal) from tiendaenlinea.item i where i.Pedido_Factura_idFactura=10),
`impuestos` = (select sum(i.costoTotal) from tiendaenlinea.item i where i.Pedido_Factura_idFactura=10)*0.16
WHERE `Factura_idFactura` = 10;

UPDATE `tiendaenlinea`.`factura`
SET
`total` = (SELECT p.total FROM tiendaenlinea.pedido p where p.Factura_idFactura=2 )
WHERE `idFactura` = 2 ;

-- subcondultas

select c.tipoDocumento, c.numeroDocumento from tiendaenlinea.cuenta c where c.primerNombre like '%a%' limit 1;

select * from tiendaenlinea.factura f where f.Cuenta_numeroDocumento=(
select  c.numeroDocumento from tiendaenlinea.cuenta c where c.primerNombre like '%carlos%' ); 


select * from item i where i.Pedido_Factura_idFactura=1; 

select u.Producto_idProducto as 'codigo produto' from (select * from item i where i.Pedido_Factura_idFactura=1) u where u.cantidad=2;

select * from tiendaenlinea.producto p where p.idProducto in ('A0001', 'E0010');


select * from tiendaenlinea.factura f where f.Cuenta_numeroDocumento = any(
select  c.numeroDocumento from tiendaenlinea.cuenta c where c.primerNombre like '%d%' );



select * from tiendaenlinea.factura f where f.Cuenta_numeroDocumento = ALL(
select  c.numeroDocumento from tiendaenlinea.cuenta c where c.primerNombre like '%carlos%');

-- joins

-- joins implicitos

select * from tiendaenlinea.producto pro, tiendaenlinea.pedido ped, tiendaenlinea.item item
where ped.Factura_idFactura=item.Pedido_Factura_idFactura and item.Producto_idProducto = pro.idProducto and ped.Factura_idFactura=1
;



-- join explicitos
select * from tiendaenlinea.producto pro inner join tiendaenlinea.pedido ped inner join tiendaenlinea.item item
where ped.Factura_idFactura=item.Pedido_Factura_idFactura and item.Producto_idProducto = pro.idProducto and ped.Factura_idFactura=1
;

select * from tiendaenlinea.producto pro inner join tiendaenlinea.pedido ped inner join tiendaenlinea.item item
on ped.Factura_idFactura=item.Pedido_Factura_idFactura and item.Producto_idProducto = pro.idProducto 
where ped.Factura_idFactura=1
;

select * from tiendaenlinea.domicilio_cuenta cu left outer join tiendaenlinea.municipio mun on mun.idMunicipio= cu.Municipio_idMunicipio;

select * from tiendaenlinea.domicilio_cuenta cu right outer join tiendaenlinea.municipio mun on mun.idMunicipio= cu.Municipio_idMunicipio;
 





