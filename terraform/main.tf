# main.tf
terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = "us-east-1"
}

# Security Group
resource "aws_security_group" "mysql_sg" {
  name        = "franchise-mysql-sg"
  description = "Permitir acceso a MySQL"

  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    description = "MySQL access from anywhere"
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "franchise-mysql-sg"
  }
}

# Instance RDS MySQL
resource "aws_db_instance" "franchise_db" {
  identifier = "franchise-database"

  engine         = "mysql"
  engine_version = "8.0.40"
  instance_class = "db.t3.micro"

  allocated_storage     = 20
  storage_type          = "gp3"
  storage_encrypted     = false

  db_name  = "franchise_db"
  username = "admin"
  password = "Admin123!"
  port     = 3306

  vpc_security_group_ids = [aws_security_group.mysql_sg.id]
  publicly_accessible    = true

  backup_retention_period = 1
  backup_window           = "03:00-04:00"
  maintenance_window      = "sun:04:00-sun:05:00"

  skip_final_snapshot = true
  deletion_protection = false

  tags = {
    Name        = "franchise-mysql"
    Environment = "development"
  }
}

output "rds_endpoint" {
  value = aws_db_instance.franchise_db.address
}

output "rds_port" {
  value = aws_db_instance.franchise_db.port
}