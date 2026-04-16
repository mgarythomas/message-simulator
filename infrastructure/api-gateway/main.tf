terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

variable "alb_listener_arn" {
  description = "The ARN of the internal ALB listener created by the Helm Ingress"
  type        = string
}

variable "vpc_id" {
  description = "The VPC ID where the EKS/ALB is deployed"
  type        = string
}

variable "private_subnet_ids" {
  description = "Subnets for the VPC Link"
  type        = list(string)
}

# HTTP API (v2)
resource "aws_apigatewayv2_api" "simulator_api" {
  name          = "message-simulator-api"
  protocol_type = "HTTP"
}

# VPC Link to reach the internal ALB securely from the DMZ/Edge API Gateway
resource "aws_apigatewayv2_vpc_link" "eks_link" {
  name               = "message-simulator-vpc-link"
  security_group_ids = [] # Attach required security groups allowing transit
  subnet_ids         = var.private_subnet_ids
}

# Integration with ALB
resource "aws_apigatewayv2_integration" "alb_integration" {
  api_id             = aws_apigatewayv2_api.simulator_api.id
  integration_type   = "HTTP_PROXY"
  integration_uri    = var.alb_listener_arn
  integration_method = "ANY"
  connection_type    = "VPC_LINK"
  connection_id      = aws_apigatewayv2_vpc_link.eks_link.id
}

# Route everything to ALB (ALB Ingress rules manage splitting Frontend and Backend)
resource "aws_apigatewayv2_route" "default_route" {
  api_id    = aws_apigatewayv2_api.simulator_api.id
  route_key = "$default"
  target    = "integrations/${aws_apigatewayv2_integration.alb_integration.id}"
}

# Default stage mapping
resource "aws_apigatewayv2_stage" "default" {
  api_id      = aws_apigatewayv2_api.simulator_api.id
  name        = "$default"
  auto_deploy = true
}

output "api_gateway_endpoint" {
  value = aws_apigatewayv2_api.simulator_api.api_endpoint
}
