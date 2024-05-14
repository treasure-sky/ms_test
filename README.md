# workoutLog api

## REST API 명세서 틀

### HTTP 메소드 - API 명(일반적으로 해당되는 컨트롤러 함수명) - URL

- request
    - header
    - parameter
        - path parameter
        - query parameter
    - body
        
        ```
        request body 내용
        ```
        
- response
    
    ```
    response body 내용
    ```
    

---

## workout controller `/api/workoutLog`

- 운동기록에 대한 요청을 관리하는 컨트롤러

| 메서드 | API 명 | 요청 URL |
| --- | --- | --- |
| POST | create | /api/workoutLog |
| GET | findAll | /api/workoutLog |
| PUT | update | /api/workoutLog/{id} |
| DELETE | delete | /api/workoutLog/{id} |

### POST - create - `/api/workoutLog`

- request
    - header
        - `Content-Type: application/json`
    - body
        
        ```
        {
        	exerciseName: int(long),
        	duration: String,
        	content: String
        }
        ```
        
- response
    
    ```
    {
    	id: int(long),
    	exerciseName: String,
    	duration: String,
    	date: String(LocalDateTime),
    	content: String
    }
    ```
    

### GET - findAll - `/api/workoutLog`

- response
    
    ```
    [
    	{
    		id: int(long),
    		exerciseName: String,
    		duration: String,
    		date: String(LocalDateTime),
    		content: String
    	},
    	...
    ]
    ```
    

### PUT - update - `/api/workoutLog/{id}`

- request
    - parameter
        - path
            - `id: int(long)` - 수정할 기록의 아이디
    - header
        - `Content-Type: application/json`
    - body
        
        ```
        {
        	exerciseName: int(long),
        	duration: String,
        	content: String
        }
        ```
        
- response
    
    ```
    {
    	id: int(long),
    	exerciseName: String,
    	duration: String,
    	date: String(LocalDateTime),
    	content: String
    }
    ```
    

### DELETE - delete - `/api/workoutLog/{id}`

- request
    - parameter
        - path
            - `id: int(long)` - 수정할 기록의 아이디

---

## 추가 실습

### GET - findPart - `/api/workoutLog/page`

- request
    - parameter
        - query
            
            
            | howMany | int | 한 페이지에 몇 개의 workoutLog를 담아 보낼지 결정 |
            | --- | --- | --- |
            | pageNum | int | 요청하는 페이지 번호 |
            - ex) 전체 0~17번의 운동기록이 있을 때, howMany=5, pageNum=1으로 요청을 하면 5번부터 10번까지의 운동기록을 workoutLogs에 담아 돌려줍니다.
            - ex) 전체 0~3번의 운동기록이 있을 떄, howMany=5, pageNum=0으로 요청을 하면 0번부터 3번까지의 운동기록을 workoutLogs에 담아 돌려줍니다.
- response
    
    ```
    {
    	workoutLogs: [
    		{
    			id: int(long),
    			exerciseName: String,
    			duration: String,
    			date: String(LocalDateTime),
    			content: String
    		},
    		...
    	],
    	maxPage: int -> 반환할 수 있는 최대 페이지의 개수
    }
    ```
