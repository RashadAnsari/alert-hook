# Alert Hook

[![Build Status][1]][2]

Alert Hook is a tool for getting your alerts from alerting services and send them to other places like messengers or etc.

## Supported Services

* **AlertManager** to **BaleMessenger**

    * API

        ````$xslt
            POST - /api/v1/alertmanager/balemessenger/{token}
        ````
        
    * Config

        ```$xslt
            alertmanager {
              balemessenger {
                api-token: "alerthook"
                bot-token: "alerthook"
                chat-id: 12345
            
                template: """
                  *Status:* {{status}}
                  *Starts At:* {{startsAt}}
                  *Ends At:* {{endsAt}}
                  *Alert Name:* {{labels.alertname}}
                  *Severity:* {{annotations.severity}}
                """
              }
            }
        ```
        
* **AlertManager** to **TelegramMessenger**

    * API

        ````$xslt
            POST - /api/v1/alertmanager/telegrammessenger/{token}
        ````
        
    * Config

        ```$xslt
            alertmanager {
              telegrammessenger {
                api-token: "alerthook"
                bot-token: "alerthook"
                chat-id: 12345
            
                template: """
                  Status: {{status}}
                  Starts At: {{startsAt}}
                  Ends At: {{endsAt}}
                  Alert Name: {{labels.alertname}}
                  Severity: {{annotations.severity}}
                """
              }
            }
        ```

[1]: https://travis-ci.org/rashadansari/alert-hook.svg?branch=master
[2]: https://travis-ci.org/rashadansari/alert-hook