-- selects

select p.ID_producto from akuavida.producto p;
select distinct(p.ID_producto) from akuavida.producto p;

select p.ID_producto, p.Nombre from akuavida.producto p;


select p.ID_producto, p.Nombre from akuavida.producto p where p.ID_producto> '100' order by p.nombre;
select p.ID_producto, p.Nombre from akuavida.producto p where p.ID_producto> '100' order by p.nombre ASC;
select p.ID_producto, p.Nombre from akuavida.producto p where p.ID_producto> '100' order by p.nombre DESC;


select p.ID_producto, p.Nombre from akuavida.producto p where p.ID_producto> '100' order by p.nombre DESC limit 2;

select p.ID_producto, p.Nombre from akuavida.producto p where p.ID_producto between '100' and '105';

select * from akuavida.cuenta c  where c.Activo is false;

select * from akuavida.producto p where p.ID_producto in ('102', '103');


select * from akuavida.producto p where p.ID_producto like '%1';
select * from akuavida.producto p where p.ID_producto like '%0%';
select * from akuavida.producto p where p.ID_producto like '1%';

select 
i.Producto_ID_producto, i.Cantidad, i.Costo_Unitario, ifnull(i.Costo_Unitario,0)*i.Cantidad as totalito
from akuavida.item i where i.Pedido_Factura_ID_Factura=1;

-- when ojo no funciona
/*no funciona por la version de mysqlselect c.Primer_Nombre, (case c.Rool when 'vendedor' then 'empleado' else 'no es empleado' end case as "cargo")  from akuavida.cuenta c;*/

select 
    c.Primer_Nombre
from
    akuavida.cuenta c
where
    binary(c.Rool) = 'adMin' or c.Rool = 'vendedor';




