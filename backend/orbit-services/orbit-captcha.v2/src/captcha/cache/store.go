package cache

const CACHE_EXPIRE = 15 * 60

type CaptchaStore struct {
	client *RedisClient
}

func NewCaptchaStore(client *RedisClient) *CaptchaStore {
	return &CaptchaStore{client}
}

func (s *CaptchaStore) Set(id string, value string) {
	err := s.client.ESet(id, value, CACHE_EXPIRE)
	if err != nil {
		panic(err)
	}
}

func (s *CaptchaStore) Get(id string, clear bool) string {
	value, err := s.client.Get(id)
	if err != nil {
		panic(err)
	}

	if clear {
		err := s.client.Delete(id)
		if err != nil {
			panic(err)
		}
	}

	result := string(value)
	return result[1:len(result)-1];
}
