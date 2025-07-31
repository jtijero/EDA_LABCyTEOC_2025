-- Mostrar todos los socios registrados
-- SELECT * FROM socio;
-- Mostrar productos adquiridos por cada socio, incluyendo tipo de producto
/*
SELECT
    s.soc_iden AS dni,
    s.soc_nom || ' ' || s.soc_ape_pat || ' ' || s.soc_ape_mat AS nombre_completo,
    p.pro_iden AS producto_id,
    tp.tip_pro_nom AS tipo_producto,
    sp.num_cuenta,
    sp.fec_apertura,
    sp.saldo,
    sp.socpro_estado
FROM socioproducto sp
JOIN socio s ON s.soc_cod = sp.id_socio
JOIN producto p ON p.pro_cod = sp.id_producto
JOIN tipoproducto tp ON tp.tip_pro_cod = p.tip_pro_cod;
*/
-- Número de productos y saldo total por cada tipo de producto
/*
SELECT
    tp.tip_pro_nom AS tipo_producto,
    COUNT(sp.id_socpro) AS total_productos,
    SUM(sp.saldo) AS saldo_total
FROM socioproducto sp
JOIN producto p ON sp.id_producto = p.pro_cod
JOIN tipoproducto tp ON p.tip_pro_cod = tp.tip_pro_cod
GROUP BY tp.tip_pro_nom;
*/
-- Promedio y saldo máximo por tipo de producto
SELECT
    tp.tip_pro_nom AS tipo_producto,
    ROUND(AVG(sp.saldo), 2) AS saldo_promedio,
    ROUND(MAX(sp.saldo), 2) AS saldo_maximo
FROM socioproducto sp
JOIN producto p ON sp.id_producto = p.pro_cod
JOIN tipoproducto tp ON p.tip_pro_cod = tp.tip_pro_cod
GROUP BY tp.tip_pro_nom;

