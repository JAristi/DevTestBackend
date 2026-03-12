output "rds_endpoint" {
  description = "Endpoint de la base de datos"
  value       = aws_db_instance.franchise_db.address
}

output "rds_port" {
  description = "Puerto de la base de datos"
  value       = aws_db_instance.franchise_db.port
}