-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.4.27-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.4.0.6659
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para baseinstituto
CREATE DATABASE IF NOT EXISTS `baseinstituto` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `baseinstituto`;

-- Volcando estructura para tabla baseinstituto.calificaciones
CREATE TABLE IF NOT EXISTS `calificaciones` (
  `idCalificacion` int(11) NOT NULL AUTO_INCREMENT,
  `dniProfesor` varchar(9) NOT NULL,
  `dniEstudiante` varchar(9) NOT NULL,
  `asignatura` varchar(50) DEFAULT '-----------',
  `fecha` date NOT NULL,
  `asistencia` tinyint(1) DEFAULT 0,
  `nota` double NOT NULL DEFAULT 0,
  PRIMARY KEY (`idCalificacion`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla baseinstituto.calificaciones: ~8 rows (aproximadamente)
INSERT INTO `calificaciones` (`idCalificacion`, `dniProfesor`, `dniEstudiante`, `asignatura`, `fecha`, `asistencia`, `nota`) VALUES
	(23, '34013599A', '50627529K', 'EDUCACIÓN FÍSICA', '2023-05-03', 1, 9.9),
	(24, '50626889W', '36002421S', 'CIENCIAS NATURALES', '2023-05-16', 0, 8.4),
	(25, '52489575X', '94617381G', 'LENGUA CASTELLANA Y LITERATURA', '2023-05-11', 0, 10),
	(26, '50626889W', '50627529K', 'BIOLOGÍA', '2023-05-09', 1, 7),
	(27, '50626889W', '50627529K', 'FILOSOFÍA', '2023-05-10', 1, 4),
	(28, '52489575X', '31018800B', 'ARTES PLÁSTICAS', '2023-05-03', 0, 6.3),
	(29, '50626889W', '13523997C', 'EDUCACIÓN FÍSICA', '2023-05-09', 1, 5.3),
	(30, '50626889W', '00411024Z', 'ARTES PLÁSTICAS', '2023-05-10', 0, 9.3);

-- Volcando estructura para tabla baseinstituto.estudiantes
CREATE TABLE IF NOT EXISTS `estudiantes` (
  `idEstudiante` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL DEFAULT '0',
  `apellido` varchar(50) NOT NULL DEFAULT '0',
  `dni` varchar(9) NOT NULL DEFAULT '0',
  `direccion` varchar(100) NOT NULL DEFAULT '0',
  `telefono` varchar(9) NOT NULL DEFAULT '0',
  `email` varchar(100) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idEstudiante`),
  UNIQUE KEY `dni` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla baseinstituto.estudiantes: ~6 rows (aproximadamente)
INSERT INTO `estudiantes` (`idEstudiante`, `nombre`, `apellido`, `dni`, `direccion`, `telefono`, `email`) VALUES
	(2, 'ANTONIO', 'ORTIZ', '50627529K', 'ASDLKJFA', '123123123', 'TASODJFA@SJDF'),
	(3, 'FEDERICO', 'LOPEZ', '36002421S', 'ASDFASDFASDF', '888888888', 'ASDF@QQ'),
	(4, 'AITANA', 'OCAÑA', '94617381G', 'ASDFASDF', '999999999', '@QQ'),
	(5, 'JAIME', 'FERNANDEZ', '31018800B', 'ASDFASF', '777555666', 'ASDFA@QQ'),
	(6, 'EVA', 'MERINO', '13523997C', 'ASDFASF', '999555111', '@QQQ'),
	(7, 'LAURA', 'SANZ', '00411024Z', 'ASDFASDF', '444555666', 'ASFASF@AA');

-- Volcando estructura para tabla baseinstituto.profesores
CREATE TABLE IF NOT EXISTS `profesores` (
  `idProfesor` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL DEFAULT '0',
  `apellido` varchar(50) NOT NULL DEFAULT '0',
  `dni` varchar(9) NOT NULL DEFAULT '0',
  `direccion` varchar(50) NOT NULL DEFAULT '0',
  `telefono` varchar(9) NOT NULL DEFAULT '0',
  `email` varchar(100) NOT NULL DEFAULT '0',
  `asignatura` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idProfesor`),
  UNIQUE KEY `dni` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla baseinstituto.profesores: ~6 rows (aproximadamente)
INSERT INTO `profesores` (`idProfesor`, `nombre`, `apellido`, `dni`, `direccion`, `telefono`, `email`, `asignatura`) VALUES
	(22, 'LAURA', 'LOPEZ', '34013599A', 'ASDFAQ', '852852852', '@QQ', 'HISTORIA'),
	(23, 'ARACELI', 'TOLEDANO', '50626889W', 'ASDFASD', '699122238', 'DA@W', 'ARTES PLÁSTICAS'),
	(24, 'JUANI', 'SANCHEZ', '52489575X', 'ASDFASDFA', '123321321', '@W', 'HISTORIA'),
	(26, 'MARÍA', 'JIMENEZ', '57661611J', 'ASDFASFD', '798798798', '@QQ', 'FÍSICA Y QUÍMICA'),
	(27, 'LEONARDO', 'PORRAS', '38677948K', 'ASDFASFD', '777777777', '@11', 'LENGUA CASTELLANA Y LITERATURA'),
	(28, 'PACO', 'FERNANDEZ', '24446122C', 'ASDFA', '111222333', '@QASD', 'FÍSICA Y QUÍMICA');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
